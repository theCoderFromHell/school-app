# GitHub Actions - Quick Reference

## ⚡ Quick Setup (2 Minutes)

### Step 1: Verify Workflow Files Exist
```bash
cd /Users/theCoderFromHell/Development/projects/school-app
ls -la .github/workflows/
```

Expected output:
```
build-and-test.yml
ci-cd-pipeline.yml
```

### Step 2: Push to GitHub
```bash
git add .github/workflows/
git commit -m "Add GitHub Actions CI/CD pipeline"
git push origin main
```

### Step 3: Create a Pull Request
- Go to GitHub repository
- Create new branch: `git checkout -b test-feature`
- Make a change
- Push and create PR
- Watch pipeline run automatically

---

## 📊 What Happens on Every PR

```
PR Created/Updated
        ↓
GitHub Actions Triggered
        ↓
✅ Checkout code
✅ Setup Java 21
✅ Compile code
✅ Run 48+ tests
✅ Build JAR
✅ Upload artifacts
✅ Comment on PR
        ↓
Result: ✅ PASSED or ❌ FAILED
```

---

## 🎯 Workflow Triggers

| Event | When | Result |
|-------|------|--------|
| Open PR | PR created | Run pipeline |
| Update PR | New commits | Re-run pipeline |
| Merge PR | Code merged | Run pipeline |
| Push to main | Direct push | Run pipeline |

---

## ✅ Pipeline Steps (In Order)

1. **Checkout code** - Download repo
2. **Setup Java 21** - Configure JDK
3. **Validate Gradle** - Check wrapper
4. **Compile** - Build source code
5. **Test** - Run 48+ tests
6. **Build JAR** - Create artifact
7. **Upload Results** - Save test reports
8. **Comment PR** - Post status

---

## 📈 Test Results

When pipeline completes:

✅ **Status Check** appears on PR
- Green checkmark = All passed
- Red X = Something failed

✅ **Comment** added to PR
- Shows build status
- Links to workflow logs
- Details about failures

✅ **Artifacts** available
- Test results XML files
- Test reports HTML
- Build logs (if failed)

---

## 🔗 Where to View Results

### In GitHub UI
1. Go to repository
2. Click **"Pull requests"**
3. Select your PR
4. Scroll to "Checks" section
5. Click "Build and Test"

### Actions Tab
1. Go to repository
2. Click **"Actions"** tab
3. Select workflow run
4. View logs in detail

### Download Results
1. Open workflow run
2. Scroll to "Artifacts"
3. Download test reports
4. Extract and view HTML reports

---

## 🚀 Common Commands

### View All Workflows
```bash
ls -la .github/workflows/
```

### Check Workflow Syntax
Go to: https://www.yamllint.com/
Paste workflow content and validate

### Run Tests Locally
```bash
./gradlew clean build
```

### View Test Results
```bash
open build/reports/tests/test/index.html  # macOS
xdg-open build/reports/tests/test/index.html  # Linux
start build/reports/tests/test/index.html  # Windows
```

---

## 🐛 If Pipeline Fails

### 1. Check the Error
- Go to Actions tab
- Click on failed workflow
- Read error message

### 2. Common Issues

**Issue: Gradle not found**
```
Solution: Workflow automatically sets up Gradle
```

**Issue: Java version mismatch**
```
Solution: Workflow uses Java 21 (matches your project)
```

**Issue: Tests fail in pipeline but pass locally**
```
Solution: 
- Pull latest code: git pull
- Run locally: ./gradlew clean build
- Check application.properties (test config)
```

### 3. Download Logs
- Go to workflow run
- Scroll to "Artifacts"
- Download build logs
- Review for errors

---

## 📋 Workflow Files

### .github/workflows/build-and-test.yml
**Purpose:** Basic build and test
**Size:** ~60 lines
**Features:** Build, test, upload artifacts

### .github/workflows/ci-cd-pipeline.yml
**Purpose:** Advanced CI/CD (RECOMMENDED)
**Size:** ~110 lines
**Features:** Build, test, reporting, PR comments

---

## ✨ What Gets Tested

When pipeline runs, these test suites execute:

```
✅ Teacher CRUD Tests (48 tests)
   ├── Repository Tests (14)
   ├── Service Tests (16)
   └── Controller Tests (18)

✅ All Other Tests (50+ tests)
   ├── Student Tests
   ├── Attendance Tests
   ├── School Tests
   └── Integration Tests

TOTAL: 100+ tests
Expected Time: 3-5 minutes
```

---

## 🎯 Best Practices

✅ **Commit workflow files to repo**
```bash
git add .github/
git commit -m "Add CI/CD pipeline"
git push
```

✅ **Create PR to test**
- Don't merge directly to main
- Use PR to verify pipeline works

✅ **Review artifacts**
- Download test results
- Check for any warnings

✅ **Monitor builds**
- Check Actions tab periodically
- Fix failures quickly

---

## 🔐 Security Notes

✅ **No secrets stored in repo**
- GitHub token auto-provided
- No credentials needed
- Secure by default

✅ **If using private dependencies:**
1. Go to Settings → Secrets
2. Add repository secret
3. Reference: `${{ secrets.SECRET_NAME }}`

---

## 📞 Troubleshooting Quick Links

### Workflow Not Running
- [ ] Verify `.github/workflows/` directory exists
- [ ] Check file names (`.yml` not `.yaml`)
- [ ] Wait 1-2 minutes for GitHub to recognize
- [ ] Refresh page in browser

### Build Failures
- [ ] Run locally first: `./gradlew build`
- [ ] Check Java version: `java -version`
- [ ] Update dependencies: `./gradlew build -U`
- [ ] Review workflow logs

### Test Failures
- [ ] Run locally: `./gradlew test`
- [ ] Check database config (H2 for tests)
- [ ] Download test artifacts from workflow
- [ ] Compare with local test output

---

## 🎓 Learning Resources

| Topic | Resource |
|-------|----------|
| GitHub Actions | https://docs.github.com/en/actions |
| Gradle Build | https://gradle.org/guides/ |
| Test Reports | https://github.com/dorny/test-reporter |
| JUnit 5 | https://junit.org/junit5/docs/ |

---

## ✅ Checklist

- [ ] Workflow files in `.github/workflows/`
- [ ] Files committed to git
- [ ] Files pushed to GitHub
- [ ] PR created to test
- [ ] Pipeline runs on PR
- [ ] Tests pass or fail as expected
- [ ] Artifacts available
- [ ] PR comments showing status

---

## 🚀 Ready to Go!

Your GitHub Actions pipeline is now ready.

**To activate:**
1. Commit workflow files
2. Push to repository
3. Create a PR
4. Watch pipeline run automatically

**That's it!** Your code will be automatically tested on every PR.

---

**Pipeline Status:** ✅ READY
**Last Updated:** December 3, 2025

