#!/usr/bin/runhaskell

import System.IO
import System.Directory
import Data.List
import Development.Shake
import Development.Shake.Command
import Development.Shake.FilePath
import Development.Shake.Util


main :: IO ()
main = shakeArgs shakeOptions{shakeFiles=".shake/"} $ do
    phony "clean" $ do
        cmd "lein clean"

    phony "test" $ do
        need ["python.deps"]
        cmd "foreman start --procfile=Procfile.test"

    "tests/.venv" %> \out -> do
        cmd "virtualenv tests/.venv"

    phony "python.deps" $ do
        alwaysRerun
        cmd "tests/.venv/bin/pip install -r tests/requirements.txt"
