package backend.mwvb.service;

import backend.mwvb.entity.Root;
import backend.mwvb.mapper.RootMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final RootMapper rootMapper;

    @Autowired
    public BookServiceImpl(RootMapper mapper) {
        rootMapper = mapper;
    }

    @Override
    public List<Root> rootsInUnit(Integer index) {
        return rootMapper.rootsInUnit(index);
    }
}
