.PHONY: clean


SHOULDIT := node_modules/shouldit/bin/shouldit

default: target/shouldit-results.json

clean:
	lein clean

target/test-output.xml: src/r2rd/*.clj test/r2rd/*.clj
	lein test-out junit $@

$(SHOULDIT):
	npm install express "git+https://github.com/avengerpenguin/ShouldIT.git"

target/shouldit-results.json: $(SHOULDIT) specs/*.md target/test-output.xml
	node_modules/shouldit/bin/shouldit \
		--specs="specs/*.md" \
		--results=target/test-output.xml \
		--junit-out=target/shouldit-junit.xml \
		--specs-out=target/shouldit-specs.json \
		--results-out=target/shouldit-results.json
