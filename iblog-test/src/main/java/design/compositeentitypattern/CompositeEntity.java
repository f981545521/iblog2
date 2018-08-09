package design.compositeentitypattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-10 上午 12:12]
 **/
public class CompositeEntity {
    private CoarseGrainedObject cgo = new CoarseGrainedObject();

    public void setData(String data1, String data2){
        cgo.setData(data1, data2);
    }

    public String[] getData(){
        return cgo.getData();
    }
}