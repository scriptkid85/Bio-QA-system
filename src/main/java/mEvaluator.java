/*
 *  Wittren by Guanyu Wang, andrew ID: guanyuw
 *  For homework 1 of 11791
 */

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.collection.base_cpm.CasObjectProcessor;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.FileUtils;
import org.apache.uima.util.ProcessTrace;

/**
 * The Evaluator Class, which count the hitnumber of the output from the NERs. At the same time, if there are
 * CASs which have exactly the same content, then discard the duplicate.
 * 
 * @param outputFile
 *          file to which the evaluation files should be written.
 */

public class mEvaluator extends CasConsumer_ImplBase implements CasObjectProcessor {
  
  long hitnumber, sampleoutnumber, annotnumber;

  File outFile;

  File inputfile;

  FileWriter fileWriter;

  HashSet<String> HSet;

  public mEvaluator() {
  }

  /**
   * Initializes this CAS Consumer with the parameters specified in the descriptor.
   * 
   * @throws ResourceInitializationException
   *           if there is error in initializing the resources
   */
  public void initialize() throws ResourceInitializationException {
    HSet = new HashSet<String>();
    hitnumber = sampleoutnumber = annotnumber = 0;

    try {
      //create a FileReader to read from the sample.out
      FileReader fr = new FileReader("src/main/resources/data/sample.out");
      BufferedReader br = new BufferedReader(fr); 
      String myreadline; 
      while (br.ready()) {
        myreadline = br.readLine();
        HSet.add(myreadline);
        sampleoutnumber++;
      }
      br.close();
      br.close();
      fr.close();


    } catch (IOException e) {
      e.printStackTrace();
    }
    // extract configuration parameter settings
    String oPath = (String) getUimaContext().getConfigParameterValue("outputFile");

    // Output file should be specified in the descriptor
    if (oPath == null) {
      throw new ResourceInitializationException(
              ResourceInitializationException.CONFIG_SETTING_ABSENT, new Object[] { "outputFile" });
    }
    // If specified output directory does not exist, try to create it
    outFile = new File(oPath.trim());
    if (outFile.getParentFile() != null && !outFile.getParentFile().exists()) {
      if (!outFile.getParentFile().mkdirs())
        throw new ResourceInitializationException(
                ResourceInitializationException.RESOURCE_DATA_NOT_VALID, new Object[] { oPath,
                    "outputFile" });
    }
    try {
      fileWriter = new FileWriter(outFile);
    } catch (IOException e) {
      throw new ResourceInitializationException(e);
    }
  }

  /**
   * Processes the CasContainer which was populated by the TextAnalysisEngines. <br>
   * In this case, the CAS index is iterated over selected annotations and printed out into an
   * output file
   * 
   * @param aCAS
   *          CasContainer which has been populated by the TAEs
   * 
   * @throws ResourceProcessException
   *           if there is an error in processing the Resource
   * 
   * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(CAS)
   */
  public synchronized void processCas(CAS aCAS) throws ResourceProcessException {
    JCas jcas;
    String prerecord = " ";
    String newrecord = " ";
    
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }

    // iterate and print annotations
    Iterator annotationIter = jcas.getAnnotationIndex(GenTag.type).iterator();

    while (annotationIter.hasNext()) {
      
      GenTag annot = (GenTag) annotationIter.next();
      newrecord = new String(annot.getLineindex() + "|" + annot.getBegin() + " " + annot.getEnd()
              + "|" + annot.getMSofa());
      
      if(!prerecord.equals(newrecord)){
        annotnumber++;
        if (HSet.contains(newrecord)) {
          hitnumber++;
        }
        prerecord = newrecord;
      }
    }
    
    float precision = hitnumber/(float)annotnumber;
    float recall = hitnumber/(float)sampleoutnumber;
    try {
      fileWriter.write("hitcount: " + hitnumber + "; sampleoutcount: " + sampleoutnumber
              + "; annotationcount: " + annotnumber + ";\n" + "Precison: " + precision + "; Recall: " 
              + recall + "\n");
      fileWriter.flush();
    } catch (IOException e) {
      throw new ResourceProcessException(e);
    }
  }

  /**
   * Called when a batch of processing is completed.
   * 
   * @param aTrace
   *          ProcessTrace object that will log events in this method.
   * @throws ResourceProcessException
   *           if there is an error in processing the Resource
   * @throws IOException
   *           if there is an IO Error
   * 
   * @see org.apache.uima.collection.CasConsumer#batchProcessComplete(ProcessTrace)
   */
  public void batchProcessComplete(ProcessTrace aTrace) throws ResourceProcessException,
          IOException {
    // nothing to do in this case as AnnotationPrinter doesnot do
    // anything cumulatively
  }

  /**
   * Called when the entire collection is completed.
   * 
   * @param aTrace
   *          ProcessTrace object that will log events in this method.
   * @throws ResourceProcessException
   *           if there is an error in processing the Resource
   * @throws IOException
   *           if there is an IO Error
   * @see org.apache.uima.collection.CasConsumer#collectionProcessComplete(ProcessTrace)
   */
  public void collectionProcessComplete(ProcessTrace aTrace) throws ResourceProcessException,
          IOException {
    if (fileWriter != null) {
      fileWriter.close();
    }
  }

  /**
   * Reconfigures the parameters of this Consumer. <br>
   * This is used in conjunction with the setConfigurationParameterValue to set the configuration
   * parameter values to values other than the ones specified in the descriptor.
   * 
   * @throws ResourceConfigurationException
   *           if the configuration parameter settings are invalid
   * 
   * @see org.apache.uima.resource.ConfigurableResource#reconfigure()
   */
  public void reconfigure() throws ResourceConfigurationException {
    super.reconfigure();
    // extract configuration parameter settings
    String oPath = (String) getUimaContext().getConfigParameterValue("outputFile");
    File oFile = new File(oPath.trim());
    // if output file has changed, close exiting file and open new
    if (!oFile.equals(this.outFile)) {
      this.outFile = oFile;
      try {
        fileWriter.close();

        // If specified output directory does not exist, try to create it
        if (oFile.getParentFile() != null && !oFile.getParentFile().exists()) {
          if (!oFile.getParentFile().mkdirs())
            throw new ResourceConfigurationException(
                    ResourceInitializationException.RESOURCE_DATA_NOT_VALID, new Object[] { oPath,
                        "outputFile" });
        }
        fileWriter = new FileWriter(oFile);
      } catch (IOException e) {
        throw new ResourceConfigurationException();
      }
    }
  }

  /**
   * Called if clean up is needed in case of exit under error conditions.
   * 
   * @see org.apache.uima.resource.Resource#destroy()
   */
  public void destroy() {
    if (fileWriter != null) {
      try {
        fileWriter.close();
      } catch (IOException e) {
        // ignore IOException on destroy
      }
    }
  }

}
