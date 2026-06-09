from flask import Flask, render_template, request, redirect, url_for

app = Flask(__name__)

products = [
    {
        "id": 1,
        "name": "Laptop Pro",
        "price": 1299.99,
        "image": "https://picsum.photos/seed/laptop1/400/300.jpg",
        "details": "A powerful laptop for professionals with 16GB RAM and a 512GB SSD."
    },
    {
        "id": 2,
        "name": "Wireless Mouse",
        "price": 25.50,
        "image": "https://picsum.photos/seed/mouse1/400/300.jpg",
        "details": "Ergonomic wireless mouse with a long-lasting battery."
    },
    {
        "id": 3,
        "name": "Mechanical Keyboard",
        "price": 89.00,
        "image": "https://picsum.photos/seed/keyboard1/400/300.jpg",
        "details": "RGB backlit mechanical keyboard with satisfying clicky keys."
    }
]

# ── TYPE ERROR 1: str + int  ────────────────────────────────────────────────
# Cannot use '+' between 'str' and 'int'
total = "Price: " + 150

# ── TYPE ERROR 3: subscript on a string variable ───────────────────────────
# product_name is a str; product_name['name'] makes no sense
product_name = "iPhone"
bad_access = product_name['name']

# ── TYPE ERROR 7: len() on a non-iterable ─────────────────────────────────
# len() expects iterable, not int
item_count = len(42)

@app.route("/")
def index3():
    return number

@app.route('/')
def index():
    return render_template('index.html', products=products)

@app.route('/product/<int:product_id>')
def product_detail(product_id):
    product = next((p for p in products if p['id'] == product_id), None)
    if product is None:
        return "Product not found", 404
    return render_template('product_detail.html', product=product)

@app.route('/add', methods=['GET', 'POST'])
def add_product():
    if request.method == 'POST':
        name = request.form['name']
        price = float(request.form['price'])
        image = request.form['image']
        details = request.form['details']

        new_product = {
            "id": products[-1]['id'] + 1 if products else 1,
            "name": name,
            "price": price,
            "image": image,
            "details": details
        }

        products.append(new_product)

        # ── TYPE ERROR 6: url_for with wrong param type ────────────────────
        # Route /product/<int:product_id> expects int, but "abc" is a str
        return redirect(url_for('product_detail', product_id="abc"))

    return render_template('add_product.html')

@app.route('/delete/<int:product_id>', methods=['POST'])
def delete_product(product_id):
    # ── TYPE ERROR 4: render_template with int instead of list ────────────
    # 'items' should be a list to be iterable in the template
    return render_template('index.html', products=999)

# Run the app
if __name__ == '__main__':
    app.run(debug=True)