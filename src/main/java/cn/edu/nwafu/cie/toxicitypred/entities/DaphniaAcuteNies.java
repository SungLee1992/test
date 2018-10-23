package cn.edu.nwafu.cie.toxicitypred.entities;

public class DaphniaAcuteNies {
    private Integer id;

    private String casNo;

    private String smiles;

    private Double ncrp;

    private Double f04ns;

    private Double bo4oo;

    private Double f08oo;

    private Double eig08Aeabo;

    private Double b02ncl;

    private String ec5048h;

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

    public Double getNcrp() {
        return ncrp;
    }

    public void setNcrp(Double ncrp) {
        this.ncrp = ncrp;
    }

    public Double getF04ns() {
        return f04ns;
    }

    public void setF04ns(Double f04ns) {
        this.f04ns = f04ns;
    }

    public Double getBo4oo() {
        return bo4oo;
    }

    public void setBo4oo(Double bo4oo) {
        this.bo4oo = bo4oo;
    }

    public Double getF08oo() {
        return f08oo;
    }

    public void setF08oo(Double f08oo) {
        this.f08oo = f08oo;
    }

    public Double getEig08Aeabo() {
        return eig08Aeabo;
    }

    public void setEig08Aeabo(Double eig08Aeabo) {
        this.eig08Aeabo = eig08Aeabo;
    }

    public Double getB02ncl() {
        return b02ncl;
    }

    public void setB02ncl(Double b02ncl) {
        this.b02ncl = b02ncl;
    }

    public String getEc5048h() {
        return ec5048h;
    }

    public void setEc5048h(String ec5048h) {
        this.ec5048h = ec5048h == null ? null : ec5048h.trim();
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