package cn.edu.nwafu.cie.toxicitypred.entities;

public class NewCalDes {
    private Integer id;

    private String casNo;

    private String smiles;

    private String cheName;

    private Double hats2e;

    private Double pw3;

    private Double homa;

    private Double rdf035u;

    private Double nrct;

    private Double h050;

    private Double nrcs;

    private Double g1s;

    private String experiment;

    private String logk;

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

    public String getCheName() {
        return cheName;
    }

    public void setCheName(String cheName) {
        this.cheName = cheName;
    }

    public Double getHats2e() {
        return hats2e;
    }

    public void setHats2e(Double hats2e) {
        this.hats2e = hats2e;
    }

    public Double getPw3() {
        return pw3;
    }

    public void setPw3(Double pw3) {
        this.pw3 = pw3;
    }

    public Double getHoma() {
        return homa;
    }

    public void setHoma(Double homa) {
        this.homa = homa;
    }

    public Double getRdf035u() {
        return rdf035u;
    }

    public void setRdf035u(Double rdf035u) {
        this.rdf035u = rdf035u;
    }

    public Double getNrct() {
        return nrct;
    }

    public void setNrct(Double nrct) {
        this.nrct = nrct;
    }

    public Double getH050() {
        return h050;
    }

    public void setH050(Double h050) {
        this.h050 = h050;
    }

    public Double getNrcs() {
        return nrcs;
    }

    public void setNrcs(Double nrcs) {
        this.nrcs = nrcs;
    }

    public Double getG1s() {
        return g1s;
    }

    public void setG1s(Double g1s) {
        this.g1s = g1s;
    }

    public String getExperiment() {
        return experiment;
    }

    public void setExperiment(String experiment) {
        this.experiment = experiment == null ? null : experiment.trim();
    }

    public String getLogk() {
        return logk;
    }

    public void setLogk(String logk) {
        this.logk = logk == null ? null : logk.trim();
    }
}