package fr.sg.fmk.service.impl;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Mapper entre le JSON envoyé par Datatables et l'objet {@link fr.sg.fmk.dto.DatatablesRequest}.
 * Pour que la mapping soit effectué l'annotation {@code DatatablesParams} doit être présente avant la
 * {@code DatatablesRequest} a binder.
 *
 * @author jntakpe
 */
public class DatatablesRequestResolver implements HandlerMethodArgumentResolver {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return null;
    }
}
