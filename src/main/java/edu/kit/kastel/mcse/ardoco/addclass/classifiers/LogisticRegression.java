package edu.kit.kastel.mcse.ardoco.addclass.classifiers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.IUpdater;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.linalg.schedule.MapSchedule;
import org.nd4j.linalg.schedule.ScheduleType;

public class LogisticRegression {
    private static final Logger logger = LogManager.getLogger(LogisticRegression.class);

    private static int seed = 1234;

    private int epochs = 1;
    private int channels = 1;
    private int outputClasses = 10;
    private int height = 28;
    private int width = 28;

    private Map<Integer, Double> learningRateSchedule = null;

    private MultiLayerNetwork model = null;

    public LogisticRegression() {
        model = null;
    }

    private MultiLayerConfiguration createConfiguration() {
        learningRateSchedule = new HashMap<>();
        learningRateSchedule.put(0, 0.06);
        learningRateSchedule.put(200, 0.05);
        learningRateSchedule.put(600, 0.028);
        learningRateSchedule.put(800, 0.0060);
        learningRateSchedule.put(1000, 0.001);

        final ConvolutionLayer layer0 = new ConvolutionLayer.Builder(5, 5).nIn(channels).stride(1, 1).nOut(20).activation(Activation.IDENTITY).build();
        final SubsamplingLayer layer1 = new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX).kernelSize(2, 2).stride(2, 2).build();
        final ConvolutionLayer layer2 = new ConvolutionLayer.Builder(5, 5).stride(1, 1).nOut(50).activation(Activation.IDENTITY).build();
        final SubsamplingLayer layer3 = layer1;
        final DenseLayer layer4 = new DenseLayer.Builder().activation(Activation.RELU).nOut(500).build();
        final OutputLayer layer5 = new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD).nOut(outputClasses)
                .activation(Activation.SOFTMAX)
                .build();

        return new NeuralNetConfiguration.Builder().seed(seed)
                .l2(0.0005)
                .updater(getUpdater())
                .weightInit(WeightInit.XAVIER)
                .list()
                .layer(0, layer0)
                .layer(1, layer1)
                .layer(2, layer2)
                .layer(3, layer3)
                .layer(4, layer4)
                .layer(5, layer5)
                .setInputType(InputType.convolutionalFlat(height, width, channels))
                .build();
    }

    private IUpdater getUpdater() {
        if (learningRateSchedule == null) {
            return new Adam(1e-3);
        } else {
            return new Nesterovs(new MapSchedule(ScheduleType.ITERATION, learningRateSchedule));
        }

    }

    private MultiLayerNetwork createModel() {
        model = new MultiLayerNetwork(createConfiguration());
        return model;
    }

    public void train(DataSetIterator trainingData, DataSetIterator testData) {
        createModel();
        model.init();
        model.setListeners(new ScoreIterationListener(100));
        if (logger.isInfoEnabled()) {
            logger.info("Total num of params: {}", model.numParams());
        }

        model.fit(trainingData, epochs);
        evaluate(testData);
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
