OUTPUTDIRECTORY = dist
MAINFILE = Robin-Prillwitz-Java-FWP

build:
	# Create Directories
	mkdir -p ${OUTPUTDIRECTORY}
	for DIR in $(shell find . -maxdepth 1 -mindepth 1 -type d \( ! -name .vscode ! -name binary ! -name venv ! -name tools \) -exec basename '{}' \;) ; do mkdir -p ${OUTPUTDIRECTORY}/$$DIR ; done

	# Initial Compilation
	xelatex -output-directory=${OUTPUTDIRECTORY} ${MAINFILE}.tex

	# Glossary/ACR Aux
	makeglossaries -d ${OUTPUTDIRECTORY} ${MAINFILE}
	# Bibliography
	# biber -D --output-directory ${OUTPUTDIRECTORY} ${MAINFILE}

	# Final Compilation
	xelatex -output-directory=${OUTPUTDIRECTORY} ${MAINFILE}.tex
	xelatex -output-directory=${OUTPUTDIRECTORY} ${MAINFILE}.tex

open:
	open ${OUTPUTDIRECTORY}/${MAINFILE}.pdf

clean:
	rm -rf ./${OUTPUTDIRECTORY}
	# rm -f *.out *.aux *.dvi *.log *-blx.bib *.blg *.bbl *.acn *.acr *.alg *.glg *.glo *.gls *.ist *.lof *.lot *.bcf *.run.xml *.tex-e *.toc *._gstmp *.acn *.acr *.alg *.aux *.bbl *.bcf *.blg
