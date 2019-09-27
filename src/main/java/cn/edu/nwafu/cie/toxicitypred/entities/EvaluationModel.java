package cn.edu.nwafu.cie.toxicitypred.entities;

import java.util.Date;

public class EvaluationModel {
    private Integer id;

    private Integer trainTn;

    private Integer trainTp;

    private Integer trainFn;

    private Integer trainFp;

    private Double trainQ;

    private Double trainSn;

    private Double trainSp;

    private Integer trainNums;

    private Integer validateTn;

    private Integer validateTp;

    private Integer validateFn;

    private Integer validateFp;

    private Double validateQ;

    private Double validateSn;

    private Double validateSp;

    private Integer validateNums;

    private String type;

    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainTn() {
        return trainTn;
    }

    public void setTrainTn(Integer trainTn) {
        this.trainTn = trainTn;
    }

    public Integer getTrainTp() {
        return trainTp;
    }

    public void setTrainTp(Integer trainTp) {
        this.trainTp = trainTp;
    }

    public Integer getTrainFn() {
        return trainFn;
    }

    public void setTrainFn(Integer trainFn) {
        this.trainFn = trainFn;
    }

    public Integer getTrainFp() {
        return trainFp;
    }

    public void setTrainFp(Integer trainFp) {
        this.trainFp = trainFp;
    }

    public Double getTrainQ() {
        return trainQ;
    }

    public void setTrainQ(Double trainQ) {
        this.trainQ = trainQ;
    }

    public Double getTrainSn() {
        return trainSn;
    }

    public void setTrainSn(Double trainSn) {
        this.trainSn = trainSn;
    }

    public Double getTrainSp() {
        return trainSp;
    }

    public void setTrainSp(Double trainSp) {
        this.trainSp = trainSp;
    }

    public Integer getTrainNums() {
        return trainNums;
    }

    public void setTrainNums(Integer trainNums) {
        this.trainNums = trainNums;
    }

    public Integer getValidateTn() {
        return validateTn;
    }

    public void setValidateTn(Integer validateTn) {
        this.validateTn = validateTn;
    }

    public Integer getValidateTp() {
        return validateTp;
    }

    public void setValidateTp(Integer validateTp) {
        this.validateTp = validateTp;
    }

    public Integer getValidateFn() {
        return validateFn;
    }

    public void setValidateFn(Integer validateFn) {
        this.validateFn = validateFn;
    }

    public Integer getValidateFp() {
        return validateFp;
    }

    public void setValidateFp(Integer validateFp) {
        this.validateFp = validateFp;
    }

    public Double getValidateQ() {
        return validateQ;
    }

    public void setValidateQ(Double validateQ) {
        this.validateQ = validateQ;
    }

    public Double getValidateSn() {
        return validateSn;
    }

    public void setValidateSn(Double validateSn) {
        this.validateSn = validateSn;
    }

    public Double getValidateSp() {
        return validateSp;
    }

    public void setValidateSp(Double validateSp) {
        this.validateSp = validateSp;
    }

    public Integer getValidateNums() {
        return validateNums;
    }

    public void setValidateNums(Integer validateNums) {
        this.validateNums = validateNums;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}