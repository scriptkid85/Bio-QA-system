/* First created by JCasGen Mon Oct 15 15:04:19 CST 2012 */

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/**
 * Updated by JCasGen Mon Oct 15 19:21:27 CST 2012
 * 
 * @generated
 */
public class GenTag_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {
    return fsGenerator;
  }

  /** @generated */
  private final FSGenerator fsGenerator = new FSGenerator() {
    public FeatureStructure createFS(int addr, CASImpl cas) {
      if (GenTag_Type.this.useExistingInstance) {
        // Return eq fs instance if already created
        FeatureStructure fs = GenTag_Type.this.jcas.getJfsFromCaddr(addr);
        if (null == fs) {
          fs = new GenTag(addr, GenTag_Type.this);
          GenTag_Type.this.jcas.putJfsFromCaddr(addr, fs);
          return fs;
        }
        return fs;
      } else
        return new GenTag(addr, GenTag_Type.this);
    }
  };

  /** @generated */
  @SuppressWarnings("hiding")
  public final static int typeIndexID = GenTag.typeIndexID;

  /**
   * @generated
   * @modifiable
   */
  @SuppressWarnings("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("GenTag");

  /** @generated */
  final Feature casFeat_Lineindex;

  /** @generated */
  final int casFeatCode_Lineindex;

  /** @generated */
  public String getLineindex(int addr) {
    if (featOkTst && casFeat_Lineindex == null)
      jcas.throwFeatMissing("Lineindex", "GenTag");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Lineindex);
  }

  /** @generated */
  public void setLineindex(int addr, String v) {
    if (featOkTst && casFeat_Lineindex == null)
      jcas.throwFeatMissing("Lineindex", "GenTag");
    ll_cas.ll_setStringValue(addr, casFeatCode_Lineindex, v);
  }

  /** @generated */
  final Feature casFeat_mSofa;

  /** @generated */
  final int casFeatCode_mSofa;

  /** @generated */
  public String getMSofa(int addr) {
    if (featOkTst && casFeat_mSofa == null)
      jcas.throwFeatMissing("mSofa", "GenTag");
    return ll_cas.ll_getStringValue(addr, casFeatCode_mSofa);
  }

  /** @generated */
  public void setMSofa(int addr, String v) {
    if (featOkTst && casFeat_mSofa == null)
      jcas.throwFeatMissing("mSofa", "GenTag");
    ll_cas.ll_setStringValue(addr, casFeatCode_mSofa, v);
  }

  /**
   * initialize variables to correspond with Cas Type and Features
   * 
   * @generated
   */
  public GenTag_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl) this.casType, getFSGenerator());

    casFeat_Lineindex = jcas.getRequiredFeatureDE(casType, "Lineindex", "uima.cas.String",
            featOkTst);
    casFeatCode_Lineindex = (null == casFeat_Lineindex) ? JCas.INVALID_FEATURE_CODE
            : ((FeatureImpl) casFeat_Lineindex).getCode();

    casFeat_mSofa = jcas.getRequiredFeatureDE(casType, "mSofa", "uima.cas.String", featOkTst);
    casFeatCode_mSofa = (null == casFeat_mSofa) ? JCas.INVALID_FEATURE_CODE
            : ((FeatureImpl) casFeat_mSofa).getCode();

  }
}
