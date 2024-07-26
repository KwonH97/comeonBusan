// 알림 권한 요청
if (Notification.permission !== "granted") {
    Notification.requestPermission();
}

// SSE 연결을 생성하는 함수
function createEventSource(url) {
    const eventSource = new EventSource(url);

    // "notice" 이벤트를 수신할 때마다 실행되는 콜백 함수
    eventSource.addEventListener('notice', function(event) {
        console.log('공지사항 이벤트 수신:', event.data); // 수신된 데이터를 콘솔에 출력
        const data = JSON.parse(event.data);
        console.log('파싱된 데이터:', data); // 파싱된 데이터를 콘솔에 출력

        // 데이터 유효성 검사
        if (!data.title || !data.content) {
            console.error('유효하지 않은 데이터 수신:', data);
            return;
        }

        // 알림 표시
        if (Notification.permission === "granted") {
            new Notification(`${data.title}`, {
                body: data.content
            });
        }
    });

    // 연결 오류가 발생했을 때 실행되는 콜백 함수
    eventSource.onerror = function(event) {
        console.error('EventSource failed:', event);
        eventSource.close(); // 연결을 닫습니다
        setTimeout(() => createEventSource(url), 3000); // 3초 후 재연결 시도
    };

    // 페이지를 떠날 때 SSE 연결 닫기
    window.addEventListener('beforeunload', function() {
        eventSource.close();
    });

    return eventSource;
}

// 호출 예시
createEventSource('http://localhost:9002/khj/events');
