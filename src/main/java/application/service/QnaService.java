package application.service;

import application.infrastructure.LocalQnaRepository;
import application.infrastructure.QnaRepository;
import application.model.Qna;
import application.service.dto.QnaWriteRequest;
import java.util.List;
import java.util.Optional;

public class QnaService {
    private final QnaRepository qnaRepository;

    public QnaService() {
        this.qnaRepository = new LocalQnaRepository();
    }

    public void write(final QnaWriteRequest qnaWriteRequest) {
        Qna qna = new Qna(qnaWriteRequest.getWriter(), qnaWriteRequest.getTitle(), qnaWriteRequest.getContents());

        qnaRepository.save(qna);
    }

    public List<Qna> findAll() {
        return qnaRepository.findAll();
    }

    public Optional<Qna> findBy(final int id) {
        return qnaRepository.findById(id);
    }
}
