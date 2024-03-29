import json
from datetime import datetime

from flask import Flask, send_from_directory, render_template, make_response

app = Flask(__name__)
content_directory = "content/"
content_root = content_directory + "cse116.json"


def load_content(content_filename):
    all_content = []
    with open(content_filename) as content_file:
        content = json.load(content_file)
        current_week_number: int = get_week_number(datetime.now())
        for week in content:
            with open(content_directory + week) as week_file:
                week_content = json.load(week_file)
                first_lesson_date_str: str = week_content.get("content")[0].get("date")
                first_lesson_date: datetime = month_day_str_to_date(first_lesson_date_str)
                week_number: int = get_week_number(first_lesson_date)
                if week_number == current_week_number:
                    week_content["current_week"] = True
                all_content.append(week_content)
    return all_content


def month_day_str_to_date(date_str: str) -> datetime:
    # Given a date string in the format "February 2", return a datetime object
    return datetime.strptime(f"{date_str} {datetime.now().year}", "%B %d %Y")


def get_week_number(date: datetime) -> int:
    # Given a datetime instance, return the week number of the year that it occurs within
    return date.isocalendar()[1]


@app.get('/')
def cse116():
    content = load_content(content_root)
    resp = make_response(render_template('cse116/cse116.html', weeks=content))
    resp.headers["X-Content-Type-Options"] = "nosniff"
    return resp


@app.get('/hw/<hw_url>')
def hw(hw_url):
    resp = make_response(render_template('hw/' + str(hw_url)))
    resp.headers["X-Content-Type-Options"] = "nosniff"
    return resp


@app.get('/static_files/<path:filename>')
def send_style(filename):
    resp = make_response(send_from_directory('static_files', filename))
    resp.headers["X-Content-Type-Options"] = "nosniff"
    return resp


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000)
