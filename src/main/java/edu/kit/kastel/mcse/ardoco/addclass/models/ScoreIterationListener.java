package edu.kit.kastel.mcse.ardoco.addclass.models;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.deeplearning4j.nn.api.Model;
import org.deeplearning4j.optimize.api.BaseTrainingListener;

public class ScoreIterationListener extends BaseTrainingListener implements Serializable {
    private static final long serialVersionUID = 4737403942992203108L;
    private static Logger logger = LogManager.getLogger(ScoreIterationListener.class);
    private int printIterations = 10;

    /**
     * @param printIterations frequency with which to print scores (i.e., every printIterations parameter updates)
     */
    public ScoreIterationListener(int printIterations) {
        this.printIterations = printIterations;
    }

    /** Default constructor printing every 10 iterations */
    public ScoreIterationListener() {
    }

    @Override
    public void iterationDone(Model model, int iteration, int epoch) {
        if (printIterations <= 0) {
            printIterations = 1;
        }
        if (iteration % printIterations == 0) {
            double score = model.score();
            logger.info("Score at iteration {} is {}", iteration, score);
        }
    }

    @Override
    public String toString() {
        return "ScoreIterationListener(" + printIterations + ")";
    }
}
