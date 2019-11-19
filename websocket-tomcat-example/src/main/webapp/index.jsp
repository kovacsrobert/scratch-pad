<html>
    <body>
        <h2>Hello World!</h2>
    </body>
</html>

<button type="button" onclick="subscribe()">subscribe</button>
<br />
<br />
<button type="button" onclick="unsubscribe()">unsubscribe</button>


<script>
    var socket = new WebSocket("ws://localhost:8080/ws-test");
    var clientId = makeId(5);

    socket.onopen = function(e) {
        console.log("[open]: ", e);
    };

    socket.onmessage = function(event) {
        var message = JSON.parse(event.data);
        console.log("[message]: ", message.data);
    };

    socket.onclose = function(event) {
        console.log("[close]: ", event);
    };

    socket.onerror = function(error) {
        console.log("[error]: ", error);
    };

    function makeId(length) {
        var result           = '';
        var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        var charactersLength = characters.length;
        for ( var i = 0; i < length; i++ ) {
            result += characters.charAt(Math.floor(Math.random() * charactersLength));
        }
        return result;
    }

    function subscribe() {
        var message = {
            event: 'subscribe',
            clientId: clientId,
            level: 'info',
            data: ''
        };
        socket.send(JSON.stringify(message));
    }

    function unsubscribe() {
        var message = {
            event: 'unsubscribe',
            clientId: clientId,
            level: 'info',
            data: ''
        };
        socket.send(JSON.stringify(message));
    }

</script>