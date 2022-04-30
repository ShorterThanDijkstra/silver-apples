package backend.mwvb.service;

import backend.mwvb.entity.Root;

import java.util.List;

public interface BookService {
    List<Root> rootsInUnit(Integer index);
}
