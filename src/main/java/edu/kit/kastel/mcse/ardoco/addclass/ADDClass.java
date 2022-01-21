/* Licensed under MIT 2022. */
package edu.kit.kastel.mcse.ardoco.addclass;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.deeplearning4j.bagofwords.vectorizer.BagOfWordsVectorizer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.ops.transforms.Transforms;

/**
 * @author Jan Keim
 *
 */
public class ADDClass {
    private static Logger log = LogManager.getLogger(ADDClass.class);

    public static void main(String[] args) throws FileNotFoundException {

        var dataLocalPath = "C:\\Users\\Jan Keim\\dl4j-examples-data\\dl4j-examples\\nlp";
        // Gets Path to Text file
        String filePath = new File(dataLocalPath, "raw_sentences.txt").getAbsolutePath();

        log.info("Load & Vectorize Sentences....");
        // Strip white space before and after for each line
        SentenceIterator iter = new BasicLineIterator(filePath);
        // Split on white spaces in the line to get words
        TokenizerFactory t = new DefaultTokenizerFactory();

        /*
         * CommonPreprocessor will apply the following regex to each token: [\d\.:,"'\(\)\[\]|/?!;]+ So, effectively all
         * numbers, punctuation symbols and some special symbols are stripped off. Additionally it forces lower case for
         * all tokens.
         */
        t.setTokenPreProcessor(new CommonPreprocessor());

        log.info("Building model....");
        Word2Vec vec = new Word2Vec.Builder().minWordFrequency(5).iterations(1).layerSize(100).seed(42).windowSize(5).iterate(iter).tokenizerFactory(t).build();

        log.info("Fitting Word2Vec model....");
        vec.fit();

        log.info("Writing word vectors to text file....");

        // Prints out the closest 10 words to a given word. An example on what to do with these Word Vectors.
        var wordToCheck = "night";
        var word2ToCheck = "day";

        log.info("Closest Words:");
        Collection<String> lst = vec.wordsNearestSum(wordToCheck, 10);
        log.info("10 Words closest to '{}': {}", wordToCheck, lst);

        var cs = Transforms.cosineSim(vec.getWordVectorMatrix(wordToCheck), vec.getWordVectorMatrix(word2ToCheck));
        log.info("Similarity (Word2Vec): {}", cs);

        log.info("Building BOW-model....");
        var bow = new BagOfWordsVectorizer.Builder().setMinWordFrequency(1).setStopWords(new ArrayList<>()).setIterator(iter).setTokenizerFactory(t).build();
        log.info("Fitting BOW model....");
        bow.fit();
        var wordBow = bow.transform(wordToCheck);
        var word2Bow = bow.transform(word2ToCheck);
        cs = Transforms.cosineSim(wordBow, word2Bow);
        log.info("Similarity (BOW): {}", cs);
        cs = Transforms.cosineSim(wordBow, wordBow);
        log.info("Self-Similarity (BOW): {}", cs);

    }
}
