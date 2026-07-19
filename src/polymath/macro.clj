(ns polymath.macro)

; https://arnebrasseur.net/2026-07-16-vertical-programming.html
(defmacro nest
  "Nesting macro, puts every form as the last of the previous one.

  This has the effect of flattening if, when, let, etc. by putting the else
  (for if) or the last (and often single) expression of the body (for when,
  let, loop etc.) out and after the form.

  This keeps the main code path vertical, while special cases branch out
  of the main path."
  [& forms]
  `(->> ~@(reverse forms)))
