package cn.edu.nwafu.cie.toxicitypred.entities;

/**
 * @author: SungLee
 * @date: 2019-09-17 08:39
 * @description: 厌氧消化模型
 **/
public class AnaerobicDigestion {

    private Integer id;

    private String casNo;

    private String smiles;

    private Double dISPm;

    private Double mor15m;

    private Double hATSe;

    private Double o060;

    private String removalRate;

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
        this.casNo = casNo;
    }

    public String getSmiles() {
        return smiles;
    }

    public void setSmiles(String smiles) {
        this.smiles = smiles;
    }

    public Double getdISPm() {
        return dISPm;
    }

    public void setdISPm(Double dISPm) {
        this.dISPm = dISPm;
    }

    public Double getMor15m() {
        return mor15m;
    }

    public void setMor15m(Double mor15m) {
        this.mor15m = mor15m;
    }

    public Double gethATSe() {
        return hATSe;
    }

    public void sethATSe(Double hATSe) {
        this.hATSe = hATSe;
    }

    public Double getO060() {
        return o060;
    }

    public void setO060(Double o060) {
        this.o060 = o060;
    }

    public String getRemovalRate() {
        return removalRate;
    }

    public void setRemovalRate(String removalRate) {
        this.removalRate = removalRate;
    }

    public String getExpValue() {
        return expValue;
    }

    public void setExpValue(String expValue) {
        this.expValue = expValue;
    }

    public String getPreValue() {
        return preValue;
    }

    public void setPreValue(String preValue) {
        this.preValue = preValue;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
}
