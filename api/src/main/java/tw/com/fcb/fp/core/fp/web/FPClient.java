package tw.com.fcb.fp.core.fp.web;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "updfpc", url = "${fp.url:http://localhost:8080}", path = "/fp/accounts")
public interface FPClient extends FPAccountApi {

}
