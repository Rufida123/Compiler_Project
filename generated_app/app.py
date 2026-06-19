from flask import Flask, render_template, request, redirect, url_for
from config import Config
import os

app = Flask(__name__)
app.config.from_object(Config)

@app.route('/')
def index():
    return 

@app.route('/add')
def add_product():
    return 

@app.route('/detail/<int:product_id>')
def product_detail(product_id):
    return 

@app.route('/test')
def test():
    return 


if __name__ == '__main__':
    app.run(debug=True)
