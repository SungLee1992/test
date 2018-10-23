package cn.edu.nwafu.cie.toxicitypred.entities;

public class FishChronic {
    private Integer id;

    private String casNo;

    private String smiles;

    private Double spmaxaEadm;

    private Double mpc07;

    private Double cats2d05Ll;

    private String lc5096h;

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

    public Double getSpmaxaEadm() {
        return spmaxaEadm;
    }

    public void setSpmaxaEadm(Double spmaxaEadm) {
        this.spmaxaEadm = spmaxaEadm;
    }

    public Double getMpc07() {
        return mpc07;
    }

    public void setMpc07(Double mpc07) {
        this.mpc07 = mpc07;
    }

    public Double getCats2d05Ll() {
        return cats2d05Ll;
    }

    public void setCats2d05Ll(Double cats2d05Ll) {
        this.cats2d05Ll = cats2d05Ll;
    }

    public String getLc5096h() {
        return lc5096h;
    }

    public void setLc5096h(String lc5096h) {
        this.lc5096h = lc5096h == null ? null : lc5096h.trim();
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