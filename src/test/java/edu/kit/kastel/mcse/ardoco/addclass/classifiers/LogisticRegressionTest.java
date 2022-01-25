package edu.kit.kastel.mcse.ardoco.addclass.classifiers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.split.FileSplit;
import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;

class LogisticRegressionTest {
    private static final Logger logger = LogManager.getLogger(LogisticRegressionTest.class);

    @Test
    @DisplayName("Test LogisticRegression with MNIST")
    void mnistTest() throws Exception {
        final String basePath = System.getProperty("java.io.tmpdir") + File.separator + "mnist" + File.separator;
        final File modelPath = new File(basePath + "mnist-model.zip");
        final String dataUrl = "http://github.com/myleott/mnist_png/raw/master/mnist_png.tar.gz";

        int height = 28;
        int width = 28;
        int channels = 1;
        int outputClasses = 10;
        int batchSize = 54;

        int seed = 1234;
        Random randNumGen = new Random(seed);

        final String path = getMnistData(basePath, dataUrl);

        logger.info("Vectorizing the data from folder {}", path);
        // vectorization of train data
        File trainData = new File(path + "training");
        FileSplit trainSplit = new FileSplit(trainData, NativeImageLoader.ALLOWED_FORMATS, randNumGen);
        // use parent directory name as the image label
        ParentPathLabelGenerator labelMaker = new ParentPathLabelGenerator();
        ImageRecordReader trainRR = new ImageRecordReader(height, width, channels, labelMaker);
        trainRR.initialize(trainSplit);
        DataSetIterator train = new RecordReaderDataSetIterator(trainRR, batchSize, 1, outputClasses);

        DataNormalization imageScaler = new ImagePreProcessingScaler();
        imageScaler.fit(train);
        train.setPreProcessor(imageScaler);

        // vectorization of test data
        File testData = new File(path + "testing");
        FileSplit testSplit = new FileSplit(testData, NativeImageLoader.ALLOWED_FORMATS, randNumGen);
        ImageRecordReader testRR = new ImageRecordReader(height, width, channels, labelMaker);
        testRR.initialize(testSplit);
        DataSetIterator test = new RecordReaderDataSetIterator(testRR, batchSize, 1, outputClasses);
        // same normalization for better results
        test.setPreProcessor(imageScaler);

        logger.info("Create and training network...");
        var logisticRegression = new LogisticRegression(); // TODO
        logisticRegression.train(train, test);

        var eval = logisticRegression.evaluate(test);

        Assertions.assertAll(//
                () -> Assertions.assertTrue(eval.f1() > 0.988), //
                () -> Assertions.assertNotNull(eval) //
        );

    }

    private static String getMnistData(final String basePath, final String dataUrl) {
        final String path = basePath + "mnist_png" + File.separator;
        if (!new File(path).exists()) {
            logger.info("Downloading data {}", dataUrl);
            String localFilePath = basePath + "mnist_png.tar.gz";
            File file = new File(localFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                try {
                    downloadAndSave(dataUrl, file);
                    extractTarArchive(file, basePath);
                } catch (IOException e) {
                    logger.error(e.getMessage(), e.getCause());
                }
            }
        } else {
            logger.info("Using the local data from folder {}", path);
        }
        return path;
    }

    private static void downloadAndSave(String url, File file) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        logger.info("Connecting to {}", url);
        try (CloseableHttpResponse response = client.execute(new HttpGet(url))) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                logger.info("Downloaded {} bytes", entity.getContentLength());
                try (FileOutputStream outstream = new FileOutputStream(file)) {
                    logger.info("Saving to the local file");
                    entity.writeTo(outstream);
                    outstream.flush();
                    logger.info("Local file saved");
                }
            }
        }
    }

    private static void extractTarArchive(File file, String folder) throws IOException {
        logger.info("Extracting archive {} into folder {}", file.getName(), folder);
        // @formatter:off
        try (FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            GzipCompressorInputStream gzip = new GzipCompressorInputStream(bis);
            TarArchiveInputStream tar = new TarArchiveInputStream(gzip)) {
       // @formatter:on
            TarArchiveEntry entry;
            while ((entry = (TarArchiveEntry) tar.getNextEntry()) != null) {
                extractEntry(entry, tar, folder);
            }
        }
        logger.info("Archive extracted");
    }

    private static void extractEntry(ArchiveEntry entry, InputStream tar, String folder) throws IOException {
        final int bufferSize = 4096;
        final String path = folder + entry.getName();
        if (entry.isDirectory()) {
            new File(path).mkdirs();
        } else {
            int count;
            byte[] data = new byte[bufferSize];
            // @formatter:off
            try (FileOutputStream os = new FileOutputStream(path);
                BufferedOutputStream dest = new BufferedOutputStream(os, bufferSize)) {
           // @formatter:off
                while ((count = tar.read(data, 0, bufferSize)) != -1) {
                    dest.write(data, 0, count);
                }
            }
        }
    }
}
