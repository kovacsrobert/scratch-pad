from flask import Flask, render_template
from redis import Redis

app = Flask(__name__)
redis = Redis(host='redis', port=6379)

@app.route('/')
def hello():
    return render_template('template.html', count=redis.incr('hits'), greetings='Hello')

@app.after_request
def apply_caching(response):
    response.headers['Cache-Control'] = 'no-cache, no-store, must-revalidate'
    return response
	
if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)