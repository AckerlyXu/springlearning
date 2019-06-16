package spring.webmvc.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
public class HomeController {
	@RequestMapping("/home")
	@ResponseBody
	public String index() {

		return "hello home";
	}

	@RequestMapping("/quotes")
	@ResponseBody
	public DeferredResult<String> quotes() {
		DeferredResult<String> deferredResult = new DeferredResult<String>();
		deferredResult.onCompletion(() -> {
			System.out.println("异步完成");
		});
		new Thread(() -> {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			deferredResult.setResult("this is result from another thread");

		}).start();
		return deferredResult;
	}

	@RequestMapping("/home/process")
	public Callable<String> process() {
		System.out.println(Thread.currentThread().getId());
		Callable<String> callable = new Callable<String>() {

			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				System.out.println(Thread.currentThread().getId());
				return "pet";
			}
		};
		return callable;
	}

	@RequestMapping("home/events")
	@ResponseBody
	public ResponseBodyEmitter handle() {
		ResponseBodyEmitter emitter = new ResponseBodyEmitter();
		new Thread(

				() -> {
					try {
						emitter.send("hello");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}).start();
		new Thread(

				() -> {
					try {
						emitter.send("world");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}).start();
		new Thread(

				() -> {

					emitter.complete();

				}).start();
		// Save the emitter somewhere..

		return emitter;
	}

	@RequestMapping(path = "/events")
	public SseEmitter handleSse() {
		SseEmitter emitter = new SseEmitter();
		// Save the emitter somewhere..
		new Thread(() -> {
			// In some other thread
			try {
				emitter.send("Hello once");
				// and again later on
				emitter.send("Hello again");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// and done at some point
			emitter.complete();
		}).start();

		return emitter;
	}

	@RequestMapping("/download")
	@ResponseBody
	public StreamingResponseBody handleDownload() {
		return new StreamingResponseBody() {

			@Override
			// 直接向写入流中写入
			public void writeTo(OutputStream outputStream) throws IOException {
				outputStream.write("HELLO sTREAM".getBytes());

			}
		};
	}

}
