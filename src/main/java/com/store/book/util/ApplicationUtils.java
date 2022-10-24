package com.store.book.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Mohammad.Asim.Khan
 *
 */
@Component
public class ApplicationUtils {

  private static final ModelMapper mapper = new ModelMapper();

  public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
    return entityList.stream().map(entity -> map(entity, outCLass)).collect(Collectors.toList());
  }

  public static <D, T> D map(final T entity, Class<D> outClass) {
    return mapper.map(entity, outClass);
  }

  public static <S, D> D map(final S source, D destination) {
    mapper.map(source, destination);
    return destination;
  }

}
