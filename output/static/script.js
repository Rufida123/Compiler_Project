// Support script for the Flask demo. Copied byte-for-byte into output/ by
// StaticSiteGenerator; it is never parsed by the compiler.
(function () {
  "use strict";

  // Confirm before a destructive delete. The template also carries an inline
  // onclick fallback, so deletion still asks for confirmation without JS enabled
  // scripting differences between the Flask view and the static snapshot.
  document.addEventListener("DOMContentLoaded", function () {
    var forms = document.querySelectorAll('form[action^="/delete/"]');
    Array.prototype.forEach.call(forms, function (form) {
      form.addEventListener("submit", function (event) {
        var name = form.getAttribute("data-product-name") || "this product";
        if (!window.confirm("Delete " + name + "? This action cannot be undone.")) {
          event.preventDefault();
        }
      });
    });
  });
})();
