# GitHub Actions CI/CD Pipeline - Setup Guide

## Overview

This guide explains the GitHub Actions workflows that have been set up for the school-app project.

---

## 🚀 What Was Created

Two GitHub Actions workflows have been configured:

### 1. **build-and-test.yml** (Basic Pipeline)
Simple workflow that runs on every PR and push

### 2. **ci-cd-pipeline.yml** (Advanced Pipeline) - RECOMMENDED
Full-featured pipeline with test reporting and PR comments

---

## 📁 File Locations

Both workflows are stored in:
```
.github/
└── workflows/
    ├── build-and-test.yml          (Basic)
    └── ci-cd-pipeline.yml          (Advanced - Recommended)
```

---

## ⚙️ Configuration

### What Triggers the Pipeline?

Both workflows trigger on:

1. **Pull Requests (PR)**
   - When PR is opened
   - When PR is synchronized (new commits)
   - When PR is reopened

2. **Push Events**
   - Push to `main` branch
   - Push to `master` branch
   - Push to `develop` branch

### How to Customize Branches

Edit the workflow file to change which branches trigger the pipeline:

```yaml
on:
  pull_request:
    branches: [ main, master, develop ]  # ← Change these
  push:
    branches: [ main, master, develop ]  # ← Change these
```

---

## 🔧 Advanced Pipeline (Recommended)

### Features

✅ **Build Steps**
- Checkout code
- Setup Java 21
- Validate Gradle wrapper
- Compile Java source
- Compile test source
- Run all tests
- Build JAR artifact

✅ **Test Reporting**
- Publish JUnit test results
- Generate detailed test reports
- Comment on PR with status
- Upload test artifacts

✅ **Performance**
- Gradle caching for faster builds
- Concurrency control (cancels old runs)
- Timeout protection (30 minutes)

✅ **Artifacts**
- Upload test results
- Upload test reports
- Upload build logs (if failure)
- Retention period (7-30 days)

---

## 📊 Pipeline Stages

```
┌─────────────────────────────────────┐
│  1. Checkout Code                   │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│  2. Setup Java 21 & Gradle Cache    │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│  3. Compile & Validate              │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│  4. Run All Tests                   │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│  5. Build JAR                       │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│  6. Upload Artifacts & Report       │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│  7. Comment on PR with Results      │
└─────────────────────────────────────┘
```

---

## ✅ How to Enable

### Step 1: Verify Repository Structure
Make sure `.github/workflows/` directory exists:
```
.github/
└── workflows/
    ├── build-and-test.yml
    └── ci-cd-pipeline.yml
```

### Step 2: Push to GitHub
```bash
cd /Users/theCoderFromHell/Development/projects/school-app
git add .github/workflows/
git commit -m "Add GitHub Actions CI/CD pipeline"
git push origin main  # or your default branch
```

### Step 3: Create a Pull Request
Create a PR to trigger the pipeline automatically

### Step 4: Monitor Workflow
- Go to: `Settings` → `Actions` → `General`
- Ensure "Allow all actions and reusable workflows" is enabled
- View workflow runs in: `Actions` tab

---

## 📖 Understanding the Workflow

### Event Triggers
```yaml
on:
  pull_request:
    branches: [ main, master, develop ]
  push:
    branches: [ main, master, develop ]
```
**Explanation:** Run on PRs and pushes to specified branches

### Job Definition
```yaml
jobs:
  build-and-test:
    runs-on: ubuntu-latest
    timeout-minutes: 30
```
**Explanation:** Run on Ubuntu with 30-minute timeout

### Steps
Each step executes sequentially and stops if one fails.

---

## 🔍 Monitoring Builds

### View in GitHub
1. Go to repository
2. Click **"Actions"** tab
3. Select workflow run
4. View logs and artifacts

### PR Checks
- Status check appears on PR
- Workflow result shown as ✅ or ❌
- Comment added to PR with details

### Artifacts
Download artifacts:
1. Go to workflow run
2. Scroll to "Artifacts" section
3. Download files (test results, reports, logs)

---

## 🐛 Troubleshooting

### Pipeline Doesn't Run
**Issue:** Workflow doesn't trigger on PR

**Solutions:**
1. Verify `.github/workflows/` directory exists
2. Check file names: `*.yml` (not `.yaml`)
3. Commit and push workflow files to repo
4. Wait 1-2 minutes for GitHub to recognize files

### Build Fails Locally but Passes in Workflow
**Issue:** Different environment

**Solutions:**
1. Check Java version (workflow uses Java 21)
2. Run locally: `./gradlew clean build`
3. Compare local vs workflow environment
4. Check gradle.properties and settings.gradle

### Tests Fail in Workflow
**Issue:** Tests pass locally but fail in CI

**Solutions:**
1. Check database configuration (H2 for tests)
2. Review test output in workflow logs
3. Download test results artifact
4. Run same tests locally: `./gradlew test`

### Gradle Cache Issues
**Issue:** Build fails due to cached dependencies

**Solutions:**
```bash
# Clear Gradle cache in workflow by modifying workflow file:
# Comment out the cache line temporarily:
# cache: gradle  # <- Comment this out
```

---

## 📊 What Gets Tested

The pipeline runs:
- **48 Teacher CRUD Tests** ✅
- **All Repository Tests** ✅
- **All Service Tests** ✅
- **All Controller Tests** ✅
- **Integration Tests** ✅

Total: **100+ test cases**

---

## 🎯 Best Practices

### 1. Keep Workflows Simple
- One main workflow per purpose
- Clear naming and comments
- Easy to understand and modify

### 2. Use Caching
- Gradle cache enabled
- Speeds up builds
- Reduces bandwidth

### 3. Set Timeouts
- 30-minute timeout prevents hanging
- Adjust if needed for your project

### 4. Artifact Retention
- Test results: 30 days
- Build logs: 7 days
- Saves storage space

### 5. PR Comments
- Automatic status comments
- Quick feedback to developers
- Improves workflow visibility

---

## 🔐 Security Considerations

### GitHub Token
```yaml
github-token: ${{secrets.GITHUB_TOKEN}}
```
- Automatically provided by GitHub
- Only valid for current workflow run
- No credentials needed

### Secrets Management
If you add sensitive data:
1. Go to: `Settings` → `Secrets and variables` → `Actions`
2. Click "New repository secret"
3. Reference in workflow: `${{ secrets.SECRET_NAME }}`

**Do NOT:**
- Commit passwords or tokens
- Store secrets in workflow files
- Log sensitive information

---

## 📈 Customization Examples

### Add Slack Notification
```yaml
- name: Notify Slack
  if: failure()
  uses: slackapi/slack-github-action@v1
  with:
    webhook-url: ${{ secrets.SLACK_WEBHOOK }}
```

### Add Code Coverage
```yaml
- name: Generate Coverage Report
  run: ./gradlew test jacocoTestReport
```

### Add SonarQube Analysis
```yaml
- name: SonarQube Scan
  uses: SonarSource/sonarcloud-github-action@master
```

### Add Performance Test
```yaml
- name: Performance Test
  run: ./gradlew test --tests "PerformanceTest"
```

---

## 📋 Workflow File Structure

```yaml
name: CI/CD Pipeline                        # Workflow name
on: ...                                     # Triggers
concurrency: ...                            # Concurrency control
jobs:
  build-and-test:                          # Job name
    runs-on: ubuntu-latest                 # Runner
    steps:                                 # Steps to execute
      - name: Step 1
        run: command
      - name: Step 2
        uses: action/name
```

---

## ✨ Summary

### What You Get

✅ Automatic builds on every PR
✅ All tests run automatically
✅ Test results in PR
✅ Build artifacts uploaded
✅ Detailed test reports
✅ PR comments with status

### Time Saved

- No manual build verification
- Catch issues before merge
- Automatic test reporting
- Reduces merge conflicts

---

## 🚀 Next Steps

1. **Push changes to GitHub**
   ```bash
   git add .github/
   git commit -m "Add CI/CD pipeline"
   git push
   ```

2. **Create a PR to test**
   - Create branch: `git checkout -b test-pipeline`
   - Make a small change
   - Push and create PR
   - Watch workflow run in "Actions" tab

3. **Customize if needed**
   - Edit `.github/workflows/ci-cd-pipeline.yml`
   - Add more checks
   - Integrate with other services

4. **Monitor builds**
   - Check Actions tab regularly
   - Download test results
   - Review PR comments

---

## 📚 Resources

- **GitHub Actions Docs:** https://docs.github.com/en/actions
- **Gradle Workflow:** https://github.com/gradle/gradle-build-action
- **Test Reporter:** https://github.com/dorny/test-reporter
- **Java Setup:** https://github.com/actions/setup-java

---

## 🆘 Getting Help

If workflow doesn't work:

1. **Check syntax:** Validate YAML at https://www.yamllint.com/
2. **Read logs:** View full logs in Actions tab
3. **GitHub docs:** https://docs.github.com/en/actions
4. **Test locally:** Run `./gradlew clean build` locally first

---

**Setup Date:** December 3, 2025
**Workflow Status:** ✅ Ready to Use
**Pipeline Type:** Advanced (Recommended)

