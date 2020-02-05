Deploy na PCF
https://docs.run.pivotal.io/buildpacks/python/index.html
1. Nadać uprawnienia skryptom<br/>
sudo chmod -R 775 ./
2. Wywołać<br/>
cf push editnow-worker
<br/>
ew.<br/>
cf push editnow-worker -c "python worker.py"