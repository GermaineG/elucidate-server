package com.digirati.elucidate.service.search.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.infrastructure.builder.AnnotationPageBuilder;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationCollectionIRIBuilder;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationPageIRIBuilder;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.service.search.AbstractAnnotationPageSearchService;

public abstract class AbstractAnnotationPageSearchServiceImpl<A extends AbstractAnnotation, P extends AbstractAnnotationPage> implements AbstractAnnotationPageSearchService<A, P> {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    private int pageSize;

    protected AbstractAnnotationPageSearchServiceImpl(int pageSize) {
        this.pageSize = pageSize;
    }

    protected abstract P convertToAnnotationPage(Map<String, Object> jsonMap);

    @Override
    public ServiceResponse<P> buildAnnotationPageByBody(List<A> annotations, List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, int page, boolean embeddedDescriptions) {

        AnnotationCollectionIRIBuilder annotationCollectionIriBuilder = () -> buildBodySearchCollectionIri(fields, value, strict, xywh, t, creatorIri);
        AnnotationPageIRIBuilder annotationPageIriBuilder = (int _page, boolean _embeddedDescriptions) -> buildBodySearchPageIri(fields, value, strict, xywh, t, creatorIri, _page, _embeddedDescriptions);

        return new AnnotationPageBuilder<A, P>(this::convertToAnnotationPage, annotationCollectionIriBuilder, annotationPageIriBuilder).buildAnnotationPage(annotations, page, embeddedDescriptions, pageSize);
    }

    protected abstract String buildBodySearchCollectionIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri);

    protected abstract String buildBodySearchPageIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, int page, boolean embeddedDescriptions);

    @Override
    public ServiceResponse<P> buildAnnotationPageByTarget(List<A> annotations, List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, int page, boolean embeddedDescriptions) {

        AnnotationCollectionIRIBuilder annotationCollectionIriBuilder = () -> buildTargetSearchCollectionIri(fields, value, strict, xywh, t, creatorIri);
        AnnotationPageIRIBuilder annotationPageIriBuilder = (int _page, boolean _embeddedDescriptions) -> buildTargetSearchPageIri(fields, value, strict, xywh, t, creatorIri, _page, _embeddedDescriptions);

        return new AnnotationPageBuilder<A, P>(this::convertToAnnotationPage, annotationCollectionIriBuilder, annotationPageIriBuilder).buildAnnotationPage(annotations, page, embeddedDescriptions, pageSize);
    }

    protected abstract String buildTargetSearchCollectionIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri);

    protected abstract String buildTargetSearchPageIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, int page, boolean embeddedDescriptions);

    @Override
    public ServiceResponse<P> buildAnnotationPageByCreator(List<A> annotations, List<String> levels, String type, String value, int page, boolean embeddedDescriptions) {

        AnnotationCollectionIRIBuilder annotationCollectionIriBuilder = () -> buildCreatorSearchCollectionIri(levels, type, value);
        AnnotationPageIRIBuilder annotationPageIriBuilder = (int _page, boolean _embeddedDescriptions) -> buildCreatorSearchPageIri(levels, type, value, page, embeddedDescriptions);

        return new AnnotationPageBuilder<A, P>(this::convertToAnnotationPage, annotationCollectionIriBuilder, annotationPageIriBuilder).buildAnnotationPage(annotations, page, embeddedDescriptions, pageSize);
    }

    protected abstract String buildCreatorSearchCollectionIri(List<String> levels, String type, String value);

    protected abstract String buildCreatorSearchPageIri(List<String> levels, String type, String value, int page, boolean embeddedDescriptions);
}
