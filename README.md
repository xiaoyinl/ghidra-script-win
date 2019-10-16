# Ghidra Scripts
This repository contains [Ghidra](https://github.com/NationalSecurityAgency/ghidra) scripts which are useful for analysis of Windows PE files.

## Installation
1. Clone this repository.
2. Copy subfolders to `<Ghidra installation directory>/Ghidra/Extensions`
3. Open Ghidra. Go to File -> Install Extensions. Select the extensions you want to enable.

## Script 1: Windows API documents searcher
This script allows you to quickly open Windows API document in your browser.

Usage: first click a function name in Decompiler panel. Then click <img src="searchWinAPI/ghidra_scripts/searchwinapi.png" alt="WinAPI Icon" title="Windows API searcher icon" width="20" height="20"> icon. The document page should show up in your default browser.

## Dependencies
The following table summarizes the dependencies of the scripts in this repository. The detailed third-party licenses are in [NOTICE.md](NOTICE.md).

File in this repo | Library Name | License
--- | --- | ---
searchWinAPI/lib/gson-2.8.6.jar | [Gson](https://github.com/google/gson) | Apache 2.0

## License
This repository is licensed under the [MIT License](LICENSE).
