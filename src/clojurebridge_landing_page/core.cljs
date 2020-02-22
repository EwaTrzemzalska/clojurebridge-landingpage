(ns ^:figwheel-hooks clojurebridge-landing-page.core
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]))

;; simple debug statement for each build
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(println (js/Date.) "Reloading: src/clojurebridge_landing_page/core.cljs")

;; Application state
;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hi world!" 
                          :another-text "Hello clojure!" 
                          :sponsors {:current {:name "Functional Works"
                                               :logo "images/functional-works-logo.png"
                                               :website "https://functional.works-hub.com/"
                                               :message "Breaking down the barriers to hiring the right software engineers,
            providing a platform to managing the whole process (written in ClojureScript)."}
                                     :past {:past-sponsors "So many past sponsors wowow"}}}))

(defn sponsor-current
  [sponsor-details]
  [:div {:class "container"}
   [:div {:class "box"}
    [:div {:class "column is-half is-8 is-offset-2"}
     [:a {:href (get-in sponsor-details [:current :website])}
      [:h3 {:class "title is-5 has-text-centered"} (str "Our sponsors: " (get-in sponsor-details [:current :name]))]]
     [:div {:class "columns"}
      [:div {:class "column"}
       [:figure {:class "image"}
        [:a {:href (get-in sponsor-details [:current :website])}
         [:img {:src "images/functional-works-logo.png"}]]]]
      [:div {:class "column"}
       [:div {:class "content"}
        [:p (get-in sponsor-details [:current :message])]]]
      ]]]])

;; Helper functions

(defn multiply [a b] (* a b))

;; Content components

(defn welcome-message []

  [:div
   [:h1 {:class "title is-1"} (:text @app-state) ]
   [:h2 {:class "title is-2"} (:another-text @app-state)]
   [:h3 {:class "title is-3"} "Wowowow"]
   
   [:section {:class "section"}
    [:h1
     {:class "title"}
     "My wonderful new content"]]])

(defn goodbye-message []
  [:section {:class "section"}
   [:h1 {:class "title is-1"}
    "Bye bye!"]])



(defn landing-page []
  (conj (welcome-message) (goodbye-message) (sponsor-current (:sponsors @app-state))))

;; System
;;;;;;;;;;;;;;;;

(defn get-app-element []
  (gdom/getElement "app"))

(defn mount [el]
  (reagent/render-component [landing-page] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
