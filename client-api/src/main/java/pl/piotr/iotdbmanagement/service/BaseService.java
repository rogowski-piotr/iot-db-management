package pl.piotr.iotdbmanagement.service;

import java.util.List;

public abstract class BaseService<T> {

    protected List<T> getPageInLimit(List<T> list, Integer limit, Integer page) {
        if (limit == null) {
            limit = 10;
        }
        if (page == null) {
            page = 0;
        }
        int indexFrom = page * limit;
        int indexTo = indexFrom + limit;
        try {
            list = list.subList(indexFrom, indexTo > list.size() ? list.size() : indexTo);
        } catch (IllegalArgumentException | NullPointerException |IndexOutOfBoundsException e) {
            list = List.of();
        }
        return list;
    }

}
