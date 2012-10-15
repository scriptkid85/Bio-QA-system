
/* First created by JCasGen Mon Oct 15 15:04:19 CST 2012 */

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;

/**
 * Updated by JCasGen Mon Oct 15 19:21:27 CST 2012 XML source:
 * /home/gavin/workspace/11791/hw1-guanyuw/src/main/resources/NERdescriptors/CollectionReader.xml
 * 
 * @generated
 */
public class GenTag extends Annotation {
  /**
   * @generated
   * @ordered
   */
  @SuppressWarnings("hiding")
  public final static int typeIndexID = JCasRegistry.register(GenTag.class);

  /**
   * @generated
   * @ordered
   */
  @SuppressWarnings("hiding")
  public final static int type = typeIndexID;

  /** @generated */
  @Override
  public int getTypeIndexID() {
    return typeIndexID;
  }

  /**
   * Never called. Disable default constructor
   * 
   * @generated
   */
  protected GenTag() {/* intentionally empty block */
  }

  /**
   * Internal - constructor used by generator
   * 
   * @generated
   */
  public GenTag(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }

  /** @generated */
  public GenTag(JCas jcas) {
    super(jcas);
    readObject();
  }

  /** @generated */
  public GenTag(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }

  /**
   * <!-- begin-user-doc --> Write your own initialization here <!-- end-user-doc -->
   * 
   * @generated modifiable
   */
  private void readObject() {/* default - does nothing empty block */
  }

  // *--------------*
  // * Feature: Lineindex

  /**
   * getter for Lineindex - gets
   * 
   * @generated
   */
  public String getLineindex() {
    if (GenTag_Type.featOkTst && ((GenTag_Type) jcasType).casFeat_Lineindex == null)
      jcasType.jcas.throwFeatMissing("Lineindex", "GenTag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GenTag_Type) jcasType).casFeatCode_Lineindex);
  }

  /**
   * setter for Lineindex - sets
   * 
   * @generated
   */
  public void setLineindex(String v) {
    if (GenTag_Type.featOkTst && ((GenTag_Type) jcasType).casFeat_Lineindex == null)
      jcasType.jcas.throwFeatMissing("Lineindex", "GenTag");
    jcasType.ll_cas.ll_setStringValue(addr, ((GenTag_Type) jcasType).casFeatCode_Lineindex, v);
  }

  // *--------------*
  // * Feature: mSofa

  /**
   * getter for mSofa - gets
   * 
   * @generated
   */
  public String getMSofa() {
    if (GenTag_Type.featOkTst && ((GenTag_Type) jcasType).casFeat_mSofa == null)
      jcasType.jcas.throwFeatMissing("mSofa", "GenTag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GenTag_Type) jcasType).casFeatCode_mSofa);
  }

  /**
   * setter for mSofa - sets
   * 
   * @generated
   */
  public void setMSofa(String v) {
    if (GenTag_Type.featOkTst && ((GenTag_Type) jcasType).casFeat_mSofa == null)
      jcasType.jcas.throwFeatMissing("mSofa", "GenTag");
    jcasType.ll_cas.ll_setStringValue(addr, ((GenTag_Type) jcasType).casFeatCode_mSofa, v);
  }
}
