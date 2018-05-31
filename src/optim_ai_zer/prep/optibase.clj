(ns optim-ai-zer.prep.optibase
  (:require [korma.core :refer :all]
            [korma.db :refer :all]))

(def articles [{:title "Two different forms of water isolated for first time" :content "Scientists have isolated the two different forms of water molecule for the first time.

Water molecules were known to exist as two distinct \"isomers\", or types, based on their slightly different properties at the atomic level.

By separating out the two isomers, researchers were able to show that they behave differently in the way that they undergo chemical reactions.

The work appears in Nature Communications.

In basic terms, water molecules consist of a single oxygen atom bonded to a pair of hydrogen atoms (H₂O).

However, they can be further subdivided based on a property of the nuclei at the hearts of the hydrogen atoms - their \"spin\".

While they aren't spinning in the sense we would understand, this property of hydrogen nuclei does affect the rotation of the water molecules themselves.

If the nuclear spins of the two hydrogen atoms in water are oriented in the same direction, it is called ortho-water. If they are arranged in different directions, it is known as para-water.

Given that the isomers are very similar, it has been particularly challenging to separate them out. But co-author Prof Stefan Willitsch and his colleagues succeeded in doing it using electric fields.

They were then able to investigate how the different forms of water reacted with another chemical.

They used ultracold diazenylium ions (a form of nitrogen) for this test. The researchers found that para-water reacted about 25% faster with the diazenylium than ortho-water.

Because the rotation of H₂O molecules is affected by the nuclear spin, different attractive forces act between the partners in this chemical reaction. The researchers supported this interpretation using computer modelling.

Prof Willitsch, from the University of Basel in Switzerland, said the research could help improve control over other kinds of chemical reaction.

\"The better one can control the states of the molecules involved in a chemical reaction, the better the underlying mechanisms and dynamics of a reaction can be investigated and understood,\" he said."}
               {:title "Grace mission launches to weigh Earth's water" :content "The first Gravity Recovery and Climate Experiment (Grace), which ran from 2002 to 2017, was widely regarded as transformative in the type of information it was able to gather, and maintaining the capability is now seen as a top priority for the American space agency (Nasa).

The follow-on mission again draws heavily on expertise from Europe, in particular from the German Research Centre for Geosciences (GFZ). Europe's biggest space company, Airbus, assembled the satellites at its factory in Friedrichshafen.

The Grace duo will obtain their data by executing a carefully calibrated pursuit in orbit.

As the lead spacecraft lurches and drags through the Earth's uneven gravity field, the second satellite will follow 220km behind, measuring changes in their separation to the nearest micron (a thousandth of a millimetre).
\"That is about a tenth of the width of a human hair over the distance between Los Angeles and San Diego,\" Prof Frank Flechtner, the Grace-FO project manager at GFZ, told BBC News.

What the Grace concept is brilliant at sensing is the big changes that occur in the hydrological cycle. "}])

(defdb db (mysql {:host "localhost"
                  :port 3306
                  :db "optima"
                  :user "optima"
                  :password "optima1"}))
(defentity article
  (entity-fields :title :content))
(defentity kword
  (entity-fields :word))
(defn insert-articles
  [articles]
  (insert article (values articles)))

(defn all-articles
  []
  (select article))

;(insert-articles articles)
(last (all-articles))
