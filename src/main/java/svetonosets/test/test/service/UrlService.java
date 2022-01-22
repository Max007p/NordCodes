package svetonosets.test.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import svetonosets.test.test.dto.*;
import svetonosets.test.test.entity.Url;
import svetonosets.test.test.entity.UrlVisitsInfo;
import svetonosets.test.test.repository.UrlRepository;
import svetonosets.test.test.repository.UrlVisitsInfoRepository;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlVisitsInfoRepository urlVisitsInfoRepository;

    @Autowired
    private BaseConversion conversion;

    @Transactional
    public String convertToShortUrl(UrlLongRequest request) {
        Url url = new Url();
        url.setLongUrl(request.getLongUrl());
        url.setExpiresDate(request.getExpiresDate());
        url.setCreatedDate(new Date());
        Url entity = urlRepository.save(url);
        return conversion.encode(entity.getId());
    }

    @Transactional
    public String getOriginalUrl(String shortUrl, HttpServletRequest request) {
        long id = conversion.decode(shortUrl);
        Url entity = urlRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no entity with " + shortUrl + " url"));

        if (entity.getExpiresDate() != null && entity.getExpiresDate().before(new Date())){
            urlRepository.delete(entity);
            throw new EntityNotFoundException("Link expired!");
        }

        addInfoOfVisiting(entity, request);

        return entity.getLongUrl();
    }

    @Transactional
    public ResponseEntity<?> deleteUrlById(long id) {
        if (urlRepository.existsById(id))
        {
            urlRepository.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Successfully deleted url!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("There is no such url!"));
        }
    }

    @Transactional
    public ResponseEntity<?> getUrlCountResponse(long urlId) {
        if (urlVisitsInfoRepository.existsByUrlId(urlId))
        {
            return ResponseEntity.ok(
                    new UrlCountResponse(urlRepository.getById(urlId).getLongUrl(),
                            urlVisitsInfoRepository.countAllByUrl(urlRepository.getById(urlId))));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("This url has not been clicked at all!"));
        }
    }

    @Transactional
    public ResponseEntity<?> getStatisticOfUrl(long urlId) {
        if (urlVisitsInfoRepository.existsByUrlId(urlId))
        {
            List<PlatformDTO> relation = new ArrayList<>(urlVisitsInfoRepository.getPlatformRelation(urlId));
            return ResponseEntity.ok(
                    new UrlStatisticResponse(urlRepository.getById(urlId).getLongUrl(),
                            urlVisitsInfoRepository.getPlatformRelation(urlId)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("This url has not been clicked at all!"));
        }
    }

    private void addInfoOfVisiting(Url entity, HttpServletRequest request) {
        UrlVisitsInfo visitedUrlInfo = new UrlVisitsInfo(
                entity,
                new Date(),
                request.getHeader("sec-ch-ua-platform"),
                request.getRemoteAddr()
        );
        urlVisitsInfoRepository.save(visitedUrlInfo);
    }
}
