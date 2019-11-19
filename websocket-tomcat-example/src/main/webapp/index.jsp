<html>
    <body>
        <h2>Hello World!</h2>
    </body>
</html>

<script>
    var socket = new WebSocket("ws://localhost:8080/ws-test");

    socket.onopen = function(e) {
        console.log("[open]: ", e);
        var message = {event: 'test', level: 'info', data: 'My name is John'};
        socket.send(JSON.stringify(message));
    };

    socket.onmessage = function(event) {
        var message = JSON.parse(event.data);
        console.log("[message]: ", event, message);
    };

    socket.onclose = function(event) {
        console.log("[close]: ", event);
    };

    socket.onerror = function(error) {
        console.log("[error]: ", error);
    };
</script>