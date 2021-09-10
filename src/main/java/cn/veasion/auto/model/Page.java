package cn.veasion.auto.model;

import java.io.Serializable;
import java.util.List;

/**
 * Page
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public class Page<T> extends R<Page.PageContent<T>> {

    public Page(List<T> data, Integer total) {
        super(PageContent.of(data, total));
    }

    public static <T> Page<T> ok(com.github.pagehelper.Page<T> page) {
        return ok(page, (int) page.getTotal());
    }

    public static <T> Page<T> ok(List<T> data, Integer total) {
        return new Page<>(data, total);
    }

    public static class PageContent<T> implements Serializable {
        private List<T> data;
        private Integer total;

        public static <T> PageContent<T> of(List<T> data, Integer total) {
            PageContent<T> content = new PageContent<>();
            content.data = data;
            content.total = total;
            return content;
        }

        public List<T> getData() {
            return data;
        }

        public void setData(List<T> data) {
            this.data = data;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getRecordsFiltered() {
            return total;
        }

        public Integer getRecordsTotal() {
            return total;
        }
    }
}
