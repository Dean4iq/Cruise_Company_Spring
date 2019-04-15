package ua.den.controller.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code Pagination} provide methods to divide list elements for pages and counts number of pages
 * for list of Objects
 *
 * @author Dean4iq
 * @version 1.0
 *
 * @param <T> Class of elements that list consists of
 */
public class Pagination<T> {
    private static final int ELEM_ON_PAGE = 10;

    /**
     * Returns list of elements for defined page
     *
     * @param entityList list to process
     * @param page number of page where list items will be displayed
     * @return list for a page
     */
    public List<T> getPageList(List<T> entityList, int page) {
        List<T> listForPage = new ArrayList<>();

        int beginIdx = (page - 1) * ELEM_ON_PAGE;
        int endIdx = beginIdx + ELEM_ON_PAGE;

        if (entityList.size() >= endIdx + 1) {
            return entityList.subList(beginIdx, endIdx);
        }

        if (entityList.size() >= beginIdx) {
            for (int i = beginIdx; i < entityList.size(); i++) {
                listForPage.add(entityList.get(i));
            }
        }

        return listForPage;
    }

    /**
     * Returns integer that allows modifying JSTL counter on JSP page
     *
     * @param currentPage number of a current page
     * @return a number to modify counter
     */
    public int getPageCountModifier(int currentPage) {
        return (currentPage - 1) * ELEM_ON_PAGE;
    }

    /**
     * Returns number of pages, depending on list size and number of elements displayed on a page
     *
     * @param entityList list to operate
     * @return number of pages
     */
    public int getPageNumber(List<T> entityList) {
        return (int) Math.ceil((double) entityList.size() / ELEM_ON_PAGE);
    }
}