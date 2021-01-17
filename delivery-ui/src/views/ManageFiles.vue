<template>
  <div>
    <div v-if="currentFile" class="progress">
      <div
        class="progress-bar progress-bar-info progress-bar-striped"
        role="progressbar"
        :aria-valuenow="progress"
        aria-valuemin="0"
        aria-valuemax="100"
        :style="{ width: progress + '%' }"
      >
        {{ progress }}%
      </div>
    </div>

    <label class="btn btn-default">
      <input type="file" ref="file" @change="selectFile"/>
    </label>

    <button class="btn btn-success" @click="upload">
      Upload
    </button>
    <b-carousel
      id="carousel-1"
      v-model="slide"
      controls
      indicators
      :interval="0"
      style="text-shadow: 1px 1px 2px #333;"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <!-- Text slides with image -->
      <b-carousel-slide v-for="index in totalPages" :key="index">
        <template #img>
          <b-row>
            <div style="min-width: 100%">
              <b-button v-for="(file, index) in fileInfos" :key="index" variant="transparent" @click="changeSelected(file)">
                <b-img thumbnail fluid :src="file.url" :alt="file.name"></b-img>
              </b-button>
            </div>
          </b-row>
        </template>
      </b-carousel-slide>
    </b-carousel>
  </div>
</template>

<script>
import FileService from '../services/files-service'
export default {
  name: 'ManageFiles',
  data () {
    return {
      selectedFiles: undefined,
      currentFile: undefined,
      progress: 0,
      message: '',
      slide: 0,
      sliding: null,
      fileInfos: [],
      currentPage: 1,
      perPage: 3,
      totalItems: 0,
      totalPages: 5,
      filters: {}
    }
  },
  mounted () {
    this.searchFiles()
  },
  methods: {
    searchFiles () {
      FileService.getFilesPage(this.filters, this.currentPage, this.perPage).then(
        response => {
          this.fileInfos = response.data.files
          this.totalItems = FileService.getFilesCount()
          this.totalPages = this.totalItems / this.perPage
        },
        error => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    selectFile () {
      this.selectedFiles = this.$refs.file.files
    },
    upload () {
      this.progress = 0

      this.currentFile = this.selectedFiles.item(0)
      FileService.upload(this.currentFile, event => {
        this.progress = Math.round((100 * event.loaded) / event.total)
      })
        .then(response => {
          this.message = response.data.message
          return FileService.getFiles()
        })
        .then(files => {
          this.fileInfos = files.data
        })
        .catch(() => {
          this.progress = 0
          this.message = 'Could not upload the file!'
          this.currentFile = undefined
        })

      this.selectedFiles = undefined
    },
    onSlideStart (slide) {
      slide = slide + 1
      this.currentPage = slide
      this.searchFiles()
      this.sliding = true
    },
    onSlideEnd (slide) {
      this.sliding = false
    },
    changeSelected (item) {
      if (this.$router.currentRoute.fullPath === '/manage/products') {
        localStorage.setItem('selectedProductImage', JSON.stringify(item))
      }
    }
  }
}
</script>

<style scoped>

</style>
