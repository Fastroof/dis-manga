/**
 * Welcome to your Workbox-powered service worker!
 *
 * You'll need to register this file in your web app and you should
 * disable HTTP caching for this file too.
 * See https://goo.gl/nhQhGp
 *
 * The rest of the code is auto-generated. Please don't update this file
 * directly; instead, make changes to your Workbox build configuration
 * and re-run your build process.
 * See https://goo.gl/2aRDsh
 */

importScripts("https://storage.googleapis.com/workbox-cdn/releases/4.3.1/workbox-sw.js");

self.addEventListener('message', (event) => {
  if (event.data && event.data.type === 'SKIP_WAITING') {
    self.skipWaiting();
  }
});

/**
 * The workboxSW.precacheAndRoute() method efficiently caches and responds to
 * requests for URLs in the manifest.
 * See https://goo.gl/S9QRab
 */
self.__precacheManifest = [
  {
    "url": "03-01.jpg",
    "revision": "61ccd31b9a99e4dc0b2115d7a181dc71"
  },
  {
    "url": "03-02.jpg",
    "revision": "0e42c83dcc174ebfdbb611ab0956fcd9"
  },
  {
    "url": "03-03.jpg",
    "revision": "dd23072447e0798b536bd162235d14c3"
  },
  {
    "url": "03-04.jpg",
    "revision": "661a64ee2776ef94b23ba82f7ee50969"
  },
  {
    "url": "03-05.jpg",
    "revision": "6f52dcb2ccb40af28a65a4b796eb918d"
  },
  {
    "url": "03-06.jpg",
    "revision": "2643698d9412e41a449c1edee719fb29"
  },
  {
    "url": "03-07.jpg",
    "revision": "c4a788f41d803f8b9bddb5adc0513be6"
  },
  {
    "url": "03-08.jpg",
    "revision": "4c810503ec4bc1adfd29904056fc2886"
  },
  {
    "url": "03-09.jpg",
    "revision": "9c798bc91f80f84b6b0e7f7fba26d65b"
  },
  {
    "url": "03-10.jpg",
    "revision": "bb5b18b65591e9d058edbc14b02c6ba5"
  },
  {
    "url": "03-11.jpg",
    "revision": "6a88d8f48c63e79c72ec45ae84a8d9ff"
  },
  {
    "url": "04-01.jpg",
    "revision": "4bbafd5dd4000461cef55f669139b17b"
  },
  {
    "url": "05-01.jpg",
    "revision": "8d54b111a4b6b5fb4cd39e4c7261b927"
  },
  {
    "url": "06-01.jpg",
    "revision": "cf2dab320c48c5ead6fe105863629e4e"
  },
  {
    "url": "06-02.jpg",
    "revision": "aa286f8fd6540b7a7b6d36392a63f505"
  },
  {
    "url": "06-03.jpg",
    "revision": "239fa5fb2a9063f1e261ae2db81fceef"
  },
  {
    "url": "06-04.jpg",
    "revision": "a2dbf924afd6a8580ce4a2d2bcd6e049"
  },
  {
    "url": "06-05.jpg",
    "revision": "22f4f542b283cdc3d027eb664424d3c8"
  },
  {
    "url": "06-06.jpg",
    "revision": "12017ddd5b9179e8f53e93044acee998"
  },
  {
    "url": "06-07.jpg",
    "revision": "1ded65fc56da9f0008ae4ef2eacc8175"
  },
  {
    "url": "1.jpg",
    "revision": "f1ea37a492254cc85dd6fd1e89b1a6b4"
  },
  {
    "url": "1.png",
    "revision": "d41d8cd98f00b204e9800998ecf8427e"
  },
  {
    "url": "12-01.jpg",
    "revision": "cc85570b3c89f73291af87791115986a"
  },
  {
    "url": "12-02.jpg",
    "revision": "f2b24c5c4beb21d5302c64b59445927a"
  },
  {
    "url": "12-03.jpg",
    "revision": "61fe98dff39d0887978deb0af605571b"
  },
  {
    "url": "12-04.jpg",
    "revision": "ff2468e7fbb03e034be0ffd7312dc7d9"
  },
  {
    "url": "12-05.jpg",
    "revision": "a126fe17514d81279c677666fd459d9d"
  },
  {
    "url": "12-06.jpg",
    "revision": "c25686b2f77ac7c5a2d4706f43e40132"
  },
  {
    "url": "12-07.jpg",
    "revision": "de17f3eb1df503250bcf1a5a4b533eec"
  },
  {
    "url": "12-08.jpg",
    "revision": "f3b58c768c18919a6fa2ab59ea6627db"
  },
  {
    "url": "13-01.jpg",
    "revision": "b22f06060909d43d796a8ffd4b0743a0"
  },
  {
    "url": "13-02.jpg",
    "revision": "2d2bca0518716a8dfaf37ef5c15e54ee"
  },
  {
    "url": "13-03.jpg",
    "revision": "fec1a3db903dc05c462fb6f384a400d6"
  },
  {
    "url": "2.jpg",
    "revision": "572bc2e4ef3efb7c81bbbcac5f09147b"
  },
  {
    "url": "3.jpg",
    "revision": "248757985bb49f73624c6923057530ac"
  },
  {
    "url": "4.jpg",
    "revision": "407c52446e2e463c4f8e494d52dbe485"
  },
  {
    "url": "404.html",
    "revision": "c5e9486a7a0c5100c15cfcf8e3668905"
  },
  {
    "url": "5.jpg",
    "revision": "7e861769b640afa00fcc0ac4b72d9c52"
  },
  {
    "url": "api/index.html",
    "revision": "f4705839d8bcdd679c5f75f947b22286"
  },
  {
    "url": "assets/css/0.styles.be148860.css",
    "revision": "da9f0b483f3ae7cbaab82178ae02c3a3"
  },
  {
    "url": "assets/img/addingBookToPersonalLibrary.497f85ee.jpg",
    "revision": "497f85ee592e5b401a43fa8c73921c23"
  },
  {
    "url": "assets/img/addingTagToBook.c3aa63e0.jpg",
    "revision": "c3aa63e0d67436777fc1a7743e10fdbe"
  },
  {
    "url": "assets/img/allTest_1.928d8707.png",
    "revision": "928d87079f075245a71da579e57be9c4"
  },
  {
    "url": "assets/img/authorization.2d103933.png",
    "revision": "2d1039337e945505b70ea9e70d108d6f"
  },
  {
    "url": "assets/img/book.bc09874b.png",
    "revision": "bc09874bb649cb6f5f6a6477dd045ebb"
  },
  {
    "url": "assets/img/CheckBookDeletion.daa1b3eb.jpg",
    "revision": "daa1b3eb5270cb62098c33573858bb45"
  },
  {
    "url": "assets/img/comment.0a082b6c.png",
    "revision": "0a082b6cc85406a10746cd4800e19904"
  },
  {
    "url": "assets/img/comment2.486fa0ef.png",
    "revision": "486fa0efaa53ac84571cbef8030d1aff"
  },
  {
    "url": "assets/img/deliteBookById.849d6ff1.jpg",
    "revision": "849d6ff17fd53afdd024b9600daf86a9"
  },
  {
    "url": "assets/img/editbook.bcf697e6.png",
    "revision": "bcf697e613dadad234027fb24888b460"
  },
  {
    "url": "assets/img/getBookByOwnerId.d0f96bf5.jpg",
    "revision": "d0f96bf58b8ba38d752ef51ea31b7fc3"
  },
  {
    "url": "assets/img/getBookInPersonalLibrary.c96a91aa.jpg",
    "revision": "c96a91aa9a619d508d4873f386cd44ca"
  },
  {
    "url": "assets/img/help-request.14b28433.jpg",
    "revision": "14b284331dfffaf4d0a98604e7161b1d"
  },
  {
    "url": "assets/img/help-request2.af5f0161.jpg",
    "revision": "af5f0161d56830c1a0e0e8907efa3046"
  },
  {
    "url": "assets/img/help-request3.e297fd2a.jpg",
    "revision": "e297fd2acb235c2642d4e7b96a1d5945"
  },
  {
    "url": "assets/img/login.396f8d3c.jpg",
    "revision": "396f8d3c2a67856c0e39e554de780f07"
  },
  {
    "url": "assets/img/login.a019e454.png",
    "revision": "a019e454cf9b2b888c7bd14bdd4fa7e9"
  },
  {
    "url": "assets/img/login2.559b3254.png",
    "revision": "559b3254a101c82900cfd81693563820"
  },
  {
    "url": "assets/img/main.ff13db48.jpg",
    "revision": "ff13db4840352c7fff3905c177a3c7f7"
  },
  {
    "url": "assets/img/newbook.2952a085.png",
    "revision": "2952a085cf9a4ddb90d040de797665ba"
  },
  {
    "url": "assets/img/newbook3.5133c6ac.png",
    "revision": "5133c6ac76b4a2e0936d1eca5e09f834"
  },
  {
    "url": "assets/img/personallibrary.6296b3fb.png",
    "revision": "6296b3fbbd971caa1298096bb50ea2a8"
  },
  {
    "url": "assets/img/personallibrary2.03c35be2.png",
    "revision": "03c35be2ea07fd7231505685c3b5a0ea"
  },
  {
    "url": "assets/img/personallibrary3.c985b36f.png",
    "revision": "c985b36fff3342df3175e8b415ca983d"
  },
  {
    "url": "assets/img/personallibrary4.243f58f3.png",
    "revision": "243f58f308007820acbf4c20f4215b85"
  },
  {
    "url": "assets/img/postBook.1f9deaa9.jpg",
    "revision": "1f9deaa9479df7c6ced87555a9be4cef"
  },
  {
    "url": "assets/img/profile.37f906ae.png",
    "revision": "37f906aebd6cc816e480818b53246946"
  },
  {
    "url": "assets/img/register.69d9c99a.jpg",
    "revision": "69d9c99a726fe349810a828221058db7"
  },
  {
    "url": "assets/img/register2.c0e88977.jpg",
    "revision": "c0e88977b1b6391d162cafd091d35914"
  },
  {
    "url": "assets/img/register3.f7c7f7b7.png",
    "revision": "f7c7f7b73a2a547dfccc09fd94caa5b2"
  },
  {
    "url": "assets/img/report.3f6f93d1.png",
    "revision": "3f6f93d1ec8c2b71db2ed5ac224f334b"
  },
  {
    "url": "assets/img/search.83621669.svg",
    "revision": "83621669651b9a3d4bf64d1a670ad856"
  },
  {
    "url": "assets/img/search.aa3fd03b.jpg",
    "revision": "aa3fd03b032355aca8e1aca397ed8f72"
  },
  {
    "url": "assets/img/tegAdded.2f5e1f8d.jpg",
    "revision": "2f5e1f8d6540a2962dec85039cc931b4"
  },
  {
    "url": "assets/img/userbooks.6ec0ae20.png",
    "revision": "6ec0ae2002c493cc5bff7cf80cc878a0"
  },
  {
    "url": "assets/js/10.e664ff5b.js",
    "revision": "bf3bd52f287a1954b7ccb2b143ed0ea7"
  },
  {
    "url": "assets/js/11.e22048a3.js",
    "revision": "a7051341ee276f12e68f4f820330d609"
  },
  {
    "url": "assets/js/12.db4fc15a.js",
    "revision": "21baaec4f6f62a87498cddc203672ddf"
  },
  {
    "url": "assets/js/13.9801c429.js",
    "revision": "32208f9f17543c447ea2b538379ac3da"
  },
  {
    "url": "assets/js/14.068ca7b1.js",
    "revision": "88eb86173befff72379783632d18b352"
  },
  {
    "url": "assets/js/15.a5098669.js",
    "revision": "e75403498d8bf22b073dc3ecc86b7a06"
  },
  {
    "url": "assets/js/16.29b3100b.js",
    "revision": "6a5ec51c2f36435a473dd274dd195fef"
  },
  {
    "url": "assets/js/17.596210d8.js",
    "revision": "89fad5f43378e0ad0396c75c1f623326"
  },
  {
    "url": "assets/js/18.5823f600.js",
    "revision": "e29fbb0b403183eb74ed0d7ee1a5c50e"
  },
  {
    "url": "assets/js/19.53dd4111.js",
    "revision": "ad61134329c12f6a59a83431426fdb7e"
  },
  {
    "url": "assets/js/2.8eb245d8.js",
    "revision": "d712ee142176017b3dca36161345acb2"
  },
  {
    "url": "assets/js/20.afa20958.js",
    "revision": "8551007af936bfcf29421cc8fbfb442d"
  },
  {
    "url": "assets/js/21.d60ec41d.js",
    "revision": "64d6eac2477a00629df36b3aa371eb48"
  },
  {
    "url": "assets/js/22.da668cda.js",
    "revision": "431592951938b92b927bac0689ec9c9f"
  },
  {
    "url": "assets/js/24.a516fa49.js",
    "revision": "a0e90f20789c62d0383b19ee33c26719"
  },
  {
    "url": "assets/js/3.c259cff4.js",
    "revision": "22971cd536606295630ba0813b6a7c62"
  },
  {
    "url": "assets/js/4.d1de1291.js",
    "revision": "9eaa05658fbc4d78eb52e2775acd6513"
  },
  {
    "url": "assets/js/5.d8de5acb.js",
    "revision": "05dbcb604c09b6bc8d282579860811b6"
  },
  {
    "url": "assets/js/6.4caf82dc.js",
    "revision": "b859b181541c9aaebd8e0228b792b8aa"
  },
  {
    "url": "assets/js/7.8e4f5e76.js",
    "revision": "48068b65a01e6866f06f36eb32e5db69"
  },
  {
    "url": "assets/js/8.fb04addb.js",
    "revision": "78d69c81c865f49b7ec53edc22aa36e0"
  },
  {
    "url": "assets/js/9.56c8f0c9.js",
    "revision": "9858677f1558a9ea48feffd3063d5a2d"
  },
  {
    "url": "assets/js/app.63c2b30c.js",
    "revision": "7a91d8bbce5062ba291ae11b474bcb2e"
  },
  {
    "url": "conclusion/index.html",
    "revision": "a407e15e03b16d43f693cedb0d406823"
  },
  {
    "url": "design/index.html",
    "revision": "41ce258c234b367152606072599aae14"
  },
  {
    "url": "index.html",
    "revision": "3c8ed4df15d4b189fa208ac45478415d"
  },
  {
    "url": "intro/index.html",
    "revision": "029fdc797f6d32b90bb355c2d6195f6c"
  },
  {
    "url": "license.html",
    "revision": "70a2edbdcd1a16b29dc6ec11e14ab937"
  },
  {
    "url": "myAvatar.png",
    "revision": "b76db1e62eb8e7fca02a487eb3eac10c"
  },
  {
    "url": "requirements/index.html",
    "revision": "a17ede719103827b54853073d333c34b"
  },
  {
    "url": "software/index.html",
    "revision": "9c4c26608845dfddc3872eac7cdc1820"
  },
  {
    "url": "test/index.html",
    "revision": "4888c1ad0549e6c63034f211e432a6df"
  }
].concat(self.__precacheManifest || []);
workbox.precaching.precacheAndRoute(self.__precacheManifest, {});
addEventListener('message', event => {
  const replyPort = event.ports[0]
  const message = event.data
  if (replyPort && message && message.type === 'skip-waiting') {
    event.waitUntil(
      self.skipWaiting().then(
        () => replyPort.postMessage({ error: null }),
        error => replyPort.postMessage({ error })
      )
    )
  }
})
