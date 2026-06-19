from flask import Flask, render_template

app = Flask(__name__)

# ============ المتغيرات ============
products = [
    {'id': 1, 'name': 'Product 1', 'price': 100},
    {'id': 2, 'name': 'Product 2', 'price': 200}
]

product = {'id': 1, 'name': 'Product 1', 'price': 100}
name = "John"
wrong_variable4 = "some value"
wrong_variable = "some value"

# ============ Routes ============

@app.route('/')
def index():
    return render_template('index.html', products=products)

@app.route('/add')
def add_product():
    return render_template('add_product.html', wrong_variable4=wrong_variable4)

@app.route('/detail/<int:product_id>')
def product_detail(product_id):
    return render_template('product_detail.html', product=product, wrong_variable=wrong_variable)

@app.route('/test')
def test():
    return render_template('test.html', name=name)

# ============ Main ============

if __name__ == '__main__':
    app.run()