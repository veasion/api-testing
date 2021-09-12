package cn.veasion.auto.model;

import java.util.List;

/**
 * Page
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public class Page<T> extends R<List<T>> {

    private Integer total;

    public Page(List<T> data, Integer total) {
        super(data);
        this.total = total;
    }

    public static <T> Page<T> ok(com.github.pagehelper.Page<T> page) {
        return ok(page, (int) page.getTotal());
    }

    public static <T> Page<T> ok(List<T> data, Integer total) {
        return new Page<>(data, total);
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
