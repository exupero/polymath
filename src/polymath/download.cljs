(ns polymath.download)

(defn download-blob! [blob filename]
  (let [link (js/document.createElement "a")]
    (set! (.-download link) filename)
    (set! (.. link -style -display) "none")
    (js/document.body.appendChild link)
    (try
      (let [url (js/URL.createObjectURL blob)]
        (set! (.-href link) url)
        (set! (.-onclick link)
              (fn []
                (js/requestAnimationFrame #(js/URL.revokeObjectURL url))))
        (.click link))
      (catch :default e
        (js/console.error e))
      (finally
        (js/document.body.removeChild link)))))

(defn download-text! [text filename]
  (download-blob! (js/Blob. #js [text] #js {:type "text/plain"}) filename))
