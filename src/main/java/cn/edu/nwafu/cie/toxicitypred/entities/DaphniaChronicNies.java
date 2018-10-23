package cn.edu.nwafu.cie.toxicitypred.entities;

public class DaphniaChronicNies {
    private Integer id;

    private String casNo;

    private String smiles;

    private Double mlogp;

    private Double spmaxEari;

    private Double mor04s;

    private Double sm02Aeadm;

    private Double rdf075s;

    private String noec21d;

    private String expValue;

    private String preValue;

    private String datatype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCasNo() {
        return casNo;
    }

    public void setCasNo(String casNo) {
        this.casNo = casNo == null ? null : casNo.trim();
    }

    public String getSmiles() {
        return smiles;
    }

    public void setSmiles(String smiles) {
        this.smiles = smiles == null ? null : smiles.trim();
    }

    public Double getMlogp() {
        return mlogp;
    }

    public void setMlogp(Double mlogp) {
        this.mlogp = mlogp;
    }

    public Double getSpmaxEari() {
        return spmaxEari;
    }

    public void setSpmaxEari(Double spmaxEari) {
        this.spmaxEari = spmaxEari;
    }

    public Double getMor04s() {
        return mor04s;
    }

    public void setMor04s(Double mor04s) {
        this.mor04s = mor04s;
    }

    public Double getSm02Aeadm() {
        return sm02Aeadm;
    }

    public void setSm02Aeadm(Double sm02Aeadm) {
        this.sm02Aeadm = sm02Aeadm;
    }

    public Double getRdf075s() {
        return rdf075s;
    }

    public void setRdf075s(Double rdf075s) {
        this.rdf075s = rdf075s;
    }

    public String getNoec21d() {
        return noec21d;
    }

    public void setNoec21d(String noec21d) {
        this.noec21d = noec21d == null ? null : noec21d.trim();
    }

    public String getExpValue() {
        return expValue;
    }

    public void setExpValue(String expValue) {
        this.expValue = expValue == null ? null : expValue.trim();
    }

    public String getPreValue() {
        return preValue;
    }

    public void setPreValue(String preValue) {
        this.preValue = preValue == null ? null : preValue.trim();
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype == null ? null : datatype.trim();
    }
}