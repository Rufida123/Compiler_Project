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
            "id": products[-1]['id'] + 1 if products else 1, # Simple way to get a new ID
            "name": name,
            "price": price,
            "image": image,
            "details": details
        }

        products.append(new_product)

        return redirect(url_for('index'))

    return render_template('add_product.html')


# Run the app
if __name__ == '__main__':
    app.run(debug=True)