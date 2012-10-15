/*
 *
 */

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * Example annotator that detects room numbers using Java 1.4 regular expressions.
 */
public class BackUpOfNErAnnotator extends JCasAnnotator_ImplBase {
  private PosTagNamedEntityRecognizer mPosTagNER;

  Map<Integer, Integer> begin2end;

  /**
   * @see JCasAnnotator_ImplBase#process(JCas)
   */
  public void process(JCas aJCas) {
    // get document text
    String docText = aJCas.getDocumentText();
    String[] lines;
    String word;
    String lineindex;
    String linewoindex;
    int firstblank;

    try {
      mPosTagNER = new PosTagNamedEntityRecognizer();
    } catch (ResourceInitializationException e) {
      e.printStackTrace();
    }

    lines = docText.split("\n");
    for (String s : lines) {
      firstblank = s.indexOf(" ");
      lineindex = s.substring(0, firstblank);
      linewoindex = s.substring(firstblank + 1, s.length());

      begin2end = mPosTagNER.getGeneSpans(linewoindex);
      Iterator it = begin2end.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry) it.next();
        // System.out.println(pairs.getKey() + " <-> " + pairs.getValue());
        GenTag annotation = new GenTag(aJCas);
        annotation.setLineindex(lineindex);
        annotation.setBegin((Integer) pairs.getKey());
        annotation.setEnd((Integer) pairs.getValue());
        word = linewoindex.substring(annotation.getBegin(), annotation.getEnd());
        annotation.setMSofa(word);
        annotation.addToIndexes();
        it.remove(); // avoids a ConcurrentModificationException
      }
    }
  }
}
