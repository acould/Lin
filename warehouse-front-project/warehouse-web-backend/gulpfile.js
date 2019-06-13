var gulp = require('gulp')
var gulpconfig = require('./config/path')

// output static path
var distPath = gulpconfig.remote || './dist/html'
var localPath = gulpconfig.local || distPath

var gulp = require('gulp'),
    fs = require('fs'),
    path = require('path'),
    less = require('gulp-less'),
    minifycss = require('gulp-minify-css'),
    uglify = require('gulp-uglify'), // js压缩
    imagemin = require('gulp-imagemin'),
    gulpif = require('gulp-if'), // 条件判断
    newer = require('gulp-newer'), // 缓存，只有替换了才压缩
    merge = require('merge-stream'),
    clean = require('gulp-clean'), // 清除
    rename = require('gulp-rename'), // 重命名
    concat = require('gulp-concat'), // 合并文件
    gutil = require('gulp-util');

// 配置文件
var config = {
    evr: false,
    // 静态资源引入
    assets: {
        js: ['./src/assets/js/**/*.js', '!./src/assets/js/{lits,plugins}/**/*.js'],
        less: './src/assets/less/**/*.less',
        css: './src/assets/css/**/*.css',
        img: './src/assets/images/**/*',
        copy: './src/assets/js/{lits,plugins}/**/*'
    },
    output: {
        remote: distPath,
        dist: {
            js: distPath + '/js',
            css: distPath + '/css',
            img: distPath + '/images',
            copy: distPath + '/js'
        },
        local: localPath
    }
}

// js task
gulp.task('js', function() {
    gulp.src(config.assets.js)
        .pipe(gulpif(config.evr, uglify()))
        .pipe(rename({ suffix: '.min' }))
        .pipe(gulp.dest(config.output.dist.js))
})

// css解析
gulp.task('css', function() {
    gulp.src(config.assets.css)
        .pipe(minifycss())
        .pipe(gulp.dest(config.output.dist.css))
})

// less解析
gulp.task('less', function() {
    gulp.src(config.assets.less)
        .pipe(less())
        .pipe(minifycss())
        .pipe(gulp.dest(config.output.dist.css))
})

// 图片
gulp.task('img', function() {
    gulp.src(config.assets.img)
        .pipe(gulp.dest(config.output.dist.img))
})

// 插件
gulp.task('copy', function() {
    gulp.src(config.assets.copy)
        .pipe(gulp.dest(config.output.dist.copy))
})

// 默认任务
gulp.task('default', ['watch'])

// dev监听改动
gulp.task('watch', function() {
    gulp.watch(config.assets.js, ['js'])
    gulp.watch(config.assets.css, ['css'])
    gulp.watch(config.assets.less, ['less'])
    gulp.watch(config.assets.img, ['img'])
    gulp.watch(config.assets.copy, ['copy'])
})

// 清除
gulp.task('clean', function() {
    return gulp.src(config.output.remote)
        .pipe(clean({ force: true }))
})

// prod编译打包
gulp.task('deploy', ['clean'], function() {
    config.evr = true
    gulp.start('js', 'css', 'less', 'img', 'copy')
})
