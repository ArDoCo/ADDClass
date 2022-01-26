package edu.kit.kastel.mcse.ardoco.addclass.models;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.IUpdater;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

public class LogisticRegression {
    private static final Logger logger = LogManager.getLogger(LogisticRegression.class);

    private static int seed = 1234;

    private int epochs;
    private int channels;
    private int outputClasses;
    private int inSizeY;
    private int inSizeX;
    private double l2;
    private IUpdater updater;
    private LossFunction lossFunction;
    private WeightInit weightInit;

    private MultiLayerNetwork model = null;

    private LogisticRegression(LogisticRegressionBuilder builder) {
        epochs = builder.epochs;
        channels = builder.channels;
        outputClasses = builder.outputClasses;
        inSizeX = builder.inSizeX;
        inSizeY = builder.inSizeY;
        l2 = builder.l2;
        updater = builder.updater;
        lossFunction = builder.lossFunction;
        weightInit = builder.weightInit;
    }

    public static class LogisticRegressionBuilder {
        private int epochs;
        private int channels;
        private int outputClasses;
        private int inSizeY;
        private int inSizeX;

        private double l2 = 0.0005;

        private IUpdater updater = new Adam(1e-3);
        private LossFunction lossFunction = LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD;
        private WeightInit weightInit = WeightInit.XAVIER;

        public LogisticRegressionBuilder(int epochs, int outputClasses, int inSizeX, int inSizeY, int channels) {
            this.epochs = epochs;
            this.outputClasses = outputClasses;
            this.inSizeX = inSizeX;
            this.inSizeY = inSizeY;
            this.channels = channels;
        }

        public LogisticRegression build() {
            return new LogisticRegression(this);
        }

        public LogisticRegressionBuilder withL2(double l2) {
            this.l2 = l2;
            return this;
        }

        public LogisticRegressionBuilder withUpdater(IUpdater updater) {
            this.updater = updater;
            return this;
        }

        public LogisticRegressionBuilder withLossFunction(LossFunction lossFunction) {
            this.lossFunction = lossFunction;
            return this;
        }

        public LogisticRegressionBuilder withWeightInit(WeightInit weightInit) {
            this.weightInit = weightInit;
            return this;
        }
    }

    public static void setSeed(int seed) {
        LogisticRegression.seed = seed;
    }

    private MultiLayerConfiguration createConfiguration() {
        final ConvolutionLayer layer0 = new ConvolutionLayer.Builder(5, 5).nIn(channels).stride(1, 1).nOut(20).activation(Activation.IDENTITY).build();
        final SubsamplingLayer layer1 = new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX).kernelSize(2, 2).stride(2, 2).build();
        final ConvolutionLayer layer2 = new ConvolutionLayer.Builder(5, 5).stride(1, 1).nOut(50).activation(Activation.IDENTITY).build();
        final SubsamplingLayer layer3 = layer1;
        final DenseLayer layer4 = new DenseLayer.Builder().activation(Activation.RELU).nOut(500).build();
        final OutputLayer layer5 = new OutputLayer.Builder(lossFunction).nOut(outputClasses).activation(Activation.SOFTMAX).build();

        return new NeuralNetConfiguration.Builder().seed(seed)
                .l2(l2)
                .updater(updater)
                .weightInit(weightInit)
                .list()
                .layer(0, layer0)
                .layer(1, layer1)
                .layer(2, layer2)
                .layer(3, layer3)
                .layer(4, layer4)
                .layer(5, layer5)
                .setInputType(InputType.convolutionalFlat(inSizeY, inSizeX, channels))
                .build();
    }

    private MultiLayerNetwork createModel() {
        model = new MultiLayerNetwork(createConfiguration());
        return model;
    }

    public void train(DataSetIterator trainingData) {
        createModel();
        model.init();
        model.setListeners(new ScoreIterationListener(100));
        if (logger.isInfoEnabled()) {
            logger.info("Total num of params: {}", model.numParams());
        }

        model.fit(trainingData, epochs);
    }

    public Evaluation evaluate(DataSetIterator testData) {
        if (model == null) {
            return null;
        }
        testData.reset();
        Evaluation eval = model.evaluate(testData);
        if (logger.isInfoEnabled()) {
            logger.info(eval.stats());
        }
        return eval;
    }

    public void saveModel(File modelPath) {
        try {
            ModelSerializer.writeModel(model, modelPath, true);
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
            return;
        }
        logger.info("The model has been saved to {}", modelPath.getPath());
    }
}
