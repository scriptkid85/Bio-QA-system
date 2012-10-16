public class GeneRuler {

  private String Matchstring[] = { "mutant", "domain", "element", "enhancer", "fusion", "chain",
      "monomer", "codon", "region", "exon", "orf", "cdna", "reporter", "gene", "antibody",
      "complex", "mrna", "oligomer", "chemokine", "subunit", "peptide", "message",
      "transactivator", "homolog", "binding site", "enhancer", "element", "allele", "isoform",
      "intron", "promoter", "operon", "releasing factor", "inhibiting factor", "hormone" };

  private String Qualifier[] = { "alpha", "beta", "gamma", "I", "II", "III", "IV", "V", "VI",
      "VII", "VIII", "IX", "X", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

  private String Hormones[] = { "Corticoliberin", "CRF", "Folliberin", "FSH-RF", "Gonadoliberinc",
      "LH", "FSH-RF", "Luliberin", "LH-RF", "LRF", "Melanoliberin", "MFR", "Melanostatin", "MIF",
      "Prolactoliberin", "PRF", "Prolactostatin", "PIF", "Somatoliberin", "SRF", "GH-RF",
      "Somatostatin", "Thyroliberin", "TRF", "Pituitary", "Choriogonadotropind", "CG",
      "Choriomammotropin", "CS", "Corticotropin", "Follitropin", "FSH", "Gonadotropine",
      "Glumitocinf", "Lipotropin", "LPH", "Lutropin", "LH", "ICSH", "Melanotropinh", "MSH",
      "Mesotocini", "OXT", "Prolactin", "mammatropin", "lactotropin", "PRL", "Somatotropin", "STH",
      "GH", "Thyrotropin", "TSH", "Urogonadotropink", "HMG", "Vasopressin", "Adiuretin", "ADH",
      "Vasotocin", "Angiotensin", "Bradykinin", "Kinin-9", "Calcitonin", "Thyrocalcitonin",
      "Erythropoietin", "Gastrin", "Glucagon", "HGF", "Insulin", "Kallidin", "Kinin-10",
      "Pancreozymin", "Cholecystokinin", "Parathyrinl", "Parathormone", "Proangiotensin",
      "Relaxin", "Secretin", "Somatomedinm", "Thymopoietinn", "Thymin" };

  private String notAllow[] = { "mutations", ")" };

  public GeneRuler() {
  }

  public boolean GeneMatch(String text) {

    int i;
    for (i = 0; i < Matchstring.length; i++) {
      if (text.toUpperCase().indexOf(Matchstring[i].toUpperCase()) != -1)
        return true;
    }

    for (i = 0; i < Qualifier.length; i++) {
      if (text.lastIndexOf(Qualifier[i]) >= Math.max(0, (text.length() - Qualifier[i].length())))
        return true;
    }
    for (i = 0; i < Hormones.length; i++) {
      if (text.toUpperCase().indexOf(Hormones[i].toUpperCase()) != -1)
        return true;
    }

    return false;
  }

  public boolean GeneNotMatch(String text) {
    int i;
    for (i = 0; i < notAllow.length; i++) {
      if (text.lastIndexOf(notAllow[i]) >= Math.max(0, (text.length() - notAllow[i].length())))
        return false;
    }
    return true;
  }

  public boolean GeneTest(String text) {
    return (GeneMatch(text) && GeneNotMatch(text));
  }
}
