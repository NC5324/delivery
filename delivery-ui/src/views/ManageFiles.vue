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
      <input type="file" ref="file" @change="selectFile" />
    </label>

    <button class="btn btn-success" :disabled="!selectedFiles" @click="upload">
      Upload
    </button>

    <div class="alert alert-light" role="alert">{{ message }}</div>

    <div class="card">
      <div class="card-header">List of Files</div>
      <ul class="list-group list-group-flush">
        <li
          class="list-group-item"
          v-for="(file, index) in fileInfos"
          :key="index"
        >
          <table style="min-width: 100%;">
            <td><strong>Name: {{ file.name }}</strong></td>
            <td>{{file.url}}<img :src="file.url" alt="img"></td>
            <td><strong>Type: {{ file.type }}</strong></td>
            <tr><strong>Size: {{ file.size }}</strong></tr>
          </table>
        </li>
      </ul>
    </div>
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

      fileInfos: []
    }
  },
  mounted () {
    FileService.getFiles().then(response => {
      this.fileInfos = response.data
    })
  },
  methods: {
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
    }
  }
}
</script>

<style scoped>

</style>
