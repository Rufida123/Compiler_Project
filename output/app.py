import json
import os
from flask import Flask, render_template, request, redirect, url_for

app = Flask(__name__)

DATA_FILE = os.path.join(os.path.dirname(__file__), "data", "products.json")


def load_products_from_json():
    if os.path.exists(DATA_FILE):
        data_file = open(DATA_FILE, "r", encoding="utf-8")
        loaded_products = json.load(data_file)
        data_file.close()
        return loaded_products
    return []


def save_products_to_json(products):
    data_file = open(DATA_FILE, "w", encoding="utf-8")
    json.dump(products, data_file, indent=2, ensure_ascii=False)
    data_file.close()


products = load_products_from_json()


@app.route("/")
def index():
    return render_template("index.html", products=products)


@app.route("/product/<int:product_id>")
def product_detail(product_id):
    product = next((p for p in products if p["id"] == product_id), None)
    if product is None:
        return "Product not found", 404
    return render_template("product_detail.html", product=product)


@app.route("/add", methods=["GET", "POST"])
def add_product():
    if request.method == "POST":
        name = request.form["name"]
        price = float(request.form["price"])
        image = request.form["image"]
        details = request.form["details"]

        new_product = {
            "id": products[-1]["id"] + 1 if products else 1,
            "name": name,
            "price": price,
            "image": image,
            "details": details
        }

        products.append(new_product)
        save_products_to_json(products)
        return redirect(url_for("index"))

    return render_template("add_product.html")


@app.route("/delete/<int:product_id>", methods=["POST"])
def delete_product(product_id):
    return render_template("index.html", products=products)


if __name__ == "__main__":
    app.run(debug=True)
