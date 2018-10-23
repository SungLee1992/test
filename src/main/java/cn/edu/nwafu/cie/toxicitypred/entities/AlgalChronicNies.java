package cn.edu.nwafu.cie.toxicitypred.entities;

public class AlgalChronicNies {
    private Integer id;

    private String casNo;

    private String smiles;

    private Double spmax6Bhm;

    private Double gats5i;

    private Double mor15s;

    private Double logkow;

    private Double ats6m;

    private String noec72h;

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

    public Double getSpmax6Bhm() {
        return spmax6Bhm;
    }

    public void setSpmax6Bhm(Double spmax6Bhm) {
        this.spmax6Bhm = spmax6Bhm;
    }

    public Double getGats5i() {
        return gats5i;
    }

    public void setGats5i(Double gats5i) {
        this.gats5i = gats5i;
    }

    public Double getMor15s() {
        return mor15s;
    }

    public void setMor15s(Double mor15s) {
        this.mor15s = mor15s;
    }

    public Double getLogkow() {
        return logkow;
    }

    public void setLogkow(Double logkow) {
        this.logkow = logkow;
    }

    public Double getAts6m() {
        return ats6m;
    }

    public void setAts6m(Double ats6m) {
        this.ats6m = ats6m;
    }

    public String getNoec72h() {
        return noec72h;
    }

    public void setNoec72h(String noec72h) {
        this.noec72h = noec72h == null ? null : noec72h.trim();
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